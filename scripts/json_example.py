# json_example.py

import json

barcodeData = '{"barcodes":[{"code":9780596007127,"storageType":"STORE"},{"code":9780596007126,"storageType":"ITEM"}],"scanType":"PUT","userId":"tre"}'

data = json.loads(barcodeData)
print ('userId = ' +  data['userId'])
print ('scanType = ' + data['scanType'])

print (data['barcodes'])

codes = data['barcodes']

for barcode in codes:
    print (barcode['code']) # code is a long
    print ('code as string = ' + str(barcode['code']))
    print ('storageType = ' + barcode['storageType'])
    print ()
