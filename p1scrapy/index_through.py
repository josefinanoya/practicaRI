
from datetime import datetime
from elasticsearch import Elasticsearch
es = Elasticsearch()
import json


#generate id as a funcion and passing counter as parameter?



with open('phones.jl') as f:
    for idx, line in enumerate(f):
        doc = json.loads(line)
        # doc['id']=idx
        doc['url']=doc['url']
        doc['title']=doc['title']
        doc['image']=doc['image']
        doc['brand']=doc['brand']
        doc['model']=doc['model']
        doc['warranty']=doc['warranty']
        doc['color']=doc['color']
        doc['intMemory']=doc['intMemory']
        doc['extMemory']=doc['extMemory']
        doc['ram']=doc['ram']
        doc['battery']=doc['battery']
        doc['os']=doc['os']
        doc['osVersion']=doc['osVersion']
        doc['processor']=doc['processor']
        doc['procVelocity']=doc['procVelocity']
        doc['screenSize']=doc['screenSize']
        doc['screenTech']=doc['screenTech']
        doc['screenRes']=doc['screenRes']
        doc['backCamNum']=doc['backCamNum']
        doc['backCamRes']=doc['backCamRes']
        doc['frontCamNum']=doc['frontCamNum']
        doc['frontCamRes']=doc['frontCamRes']
        doc['vidRecord']=doc['vidRecord']
        doc['fingerprint']=doc['fingerprint']
        doc['price']=doc['price']


        res = es.index(index="phones", id=idx, body=doc)

print()