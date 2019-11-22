# -*- coding: utf-8 -*-
#

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html
import json
from scrapy.exceptions import DropItem


class P1ScrapyPipeline(object):
    def open_spider(self, spider):
        self.file = open('phones.jl', 'w')

    def close_spider(self, spider):
        self.file.close()

    def process_item(self, item, spider):
        if not item.get('url').startswith('https://www.worten.es/productos/moviles-smartphones/smartphones') or "?page=" in item.get('url'):
            raise DropItem('Not mobile phone')
        if item.get('price') is None:
            raise DropItem('Not available at Worten')

        
        line = json.dumps(dict(item)) + "\n"
        self.file.write(line)
        return item
