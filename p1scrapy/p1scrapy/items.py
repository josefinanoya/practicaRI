# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

from scrapy import Item, Field


class PhoneItem(Item):
    url = Field()
    title = Field()
    image = Field()
    brand = Field()
    model = Field()
    warranty = Field()
    color = Field()
    intMemory = Field()
    extMemory = Field()
    ram = Field()
    battery = Field()
    os = Field()
    osVersion = Field()
    processor = Field()
    procVelocity = Field()
    screenSize = Field()
    screenTech = Field()
    screenRes = Field()
    backCamNum = Field()
    backCamRes = Field()
    frontCamNum = Field()
    frontCamRes = Field()
    vidRecord = Field()
    fingerprint = Field()
    price = Field()