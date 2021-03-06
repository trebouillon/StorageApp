# server.py

from __future__ import print_function
import cx_Oracle
import socket
import json

connection = cx_Oracle.connect("web_Socket", "17und4SRFdb", "localhost:1522/SRFSTORAGE")


def processDatabase(jobType):
    print(jobType)
    cursor = connection.cursor()
    table = "leer"
    if jobType == "loanlog":

        # cursor.execute ("""SELECT * FROM (SELECT * FROM SOCKET.loanlog ORDER BY ID DESC LIMIT 0,50) ORDER BY ID ASC""")
        cursor.execute("""SELECT * FROM SOCKET.loanlog""")
        print("select loanlog from DB")
        table = cursor.fetchall()
    cursor.close()
    connection.commit()
    print(table)
    print("get from DB / send to Socket")


    return json.dump(table)

def client_thread(conn, ip, port, MAX_BUFFER_SIZE = 4096):

    # the input is in bytes, so decode it
    input_from_client_bytes = conn.recv(MAX_BUFFER_SIZE)

    # MAX_BUFFER_SIZE is how big the message can be
    # this is test if it's sufficiently big
    import sys
    siz = sys.getsizeof(input_from_client_bytes)
    if  siz >= MAX_BUFFER_SIZE:
        print("The length of input is probably too long: {}".format(siz))

    # decode input and strip the end of line
    input_from_client = input_from_client_bytes.decode("utf8").rstrip()
    print(input_from_client)
    res = processDatabase(input_from_client)
    print("Result of processing {} is: {}".format(input_from_client, res))

    vysl = res.encode("utf8")  # encode the result string
    conn.sendall(vysl)  # send it to client
    conn.close()  # close connection
    print('Connection ' + ip + ':' + port + " ended")

def start_server():

    soc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # this is for easy starting/killing the app
    soc.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    print('Socket created')

    try:
        soc.bind(("134.30.10.176", 28088))
        print('Socket bind complete')
    except socket.error as msg:
        import sys
        print('Bind failed. Error : ' + str(sys.exc_info()))
        sys.exit()

    #Start listening on socket
    soc.listen(10)
    print('Socket now listening')

    # for handling task in separate jobs we need threading
    from threading import Thread

    # this will make an infinite loop needed for 
    # not reseting server for every client
    while True:
        conn, addr = soc.accept()
        ip, port = str(addr[0]), str(addr[1])
        print('Accepting connection from ' + ip + ':' + port)
        try:
            Thread(target=client_thread, args=(conn, ip, port)).start()
        except:
            print("Terrible error!")
            import traceback
            traceback.print_exc()
    soc.close()

start_server() 
