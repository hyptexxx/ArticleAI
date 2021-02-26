import requests
import json
import numpy as np

req = requests.get('http://rlv.unitech-mo.ru/api/v1/kmeans/yake')
req = req.json()

with open('Json_File', 'w') as js_date:
    json.dump(req,js_date)

with open('Json_File', 'r') as js_dcoding:
    req = json.load(js_dcoding)

print(type(req))
print(req[0])
