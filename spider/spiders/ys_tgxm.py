# -*- coding: utf-8 -*-
"""
Created on 2022-04-04 16:23:16
---------
@summary: 2009年以来已取得预售许可的商品房项目
---------
@author: wth
"""

import feapder
from items import 不动产登记中心_商品房预售许可_item


class YsTgxm(feapder.AirSpider):
    def start_requests(self):
        data={
            "pageNo": 1,
            "pageSize": 1297,
            "xzqh":"",
            "xmmc":"",
            "kfs.kfsmc":""
        }
        yield feapder.Request(url="http://124.93.228.101:8087/bd/tgxm",method="post",data=data)

    def parse(self, request, response):
        trs = response.xpath('//tr[@class="info"]')
        for tr in trs:
            区域名称 = tr.xpath("./td[1]/text()").extract_first()
            项目名称 = tr.xpath("./td[2]/a/text()").extract_first()
            项目链接 = tr.xpath("./td[2]/a/@href").extract_first()
            项目地址 = tr.xpath("./td[3]/text()").extract_first()
            开发企业名称 = tr.xpath("./td[4]/text()").extract_first()
            item = 不动产登记中心_商品房预售许可_item.不动产登记中心商品房预售许可Item()
            item.区域名称 = 区域名称
            item.项目名称 = 项目名称
            item.项目链接 = 项目链接
            item.项目地址 = 项目地址
            item.开发企业名称 = 开发企业名称
            yield  item

if __name__ == "__main__":
    YsTgxm().start()