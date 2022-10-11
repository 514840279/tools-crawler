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
from feapder.db.mysqldb import MysqlDB

class JpTgxm(feapder.AirSpider):
    db = MysqlDB()

    def start_requests(self):
        for a in range(1, 67):
            data = {
                "pageNo": a,
                "pagenum": a,
                "selYsxk": "xmmc",
                "txtkey": ""
            }
            # yield feapder.Request(url="http://www.fczw.cn/ysxkzList.xhtml?method=doQuery", method="post", data=data)
            yield feapder.Request(url="http://fcjyw.dlhitech.gov.cn/ysxkzList.xhtml?method=doQuery", method="post", data=data)

    def parse(self, request, response):
        trs = response.xpath('//form[@name="ysxkzForm"]/table//tr')

        for tr in trs:
            a = tr.xpath('./td[1][@style="text-align:left "]/a[@target="_blank"]/@href').extract_first()
            if a is not None:
                项目名称 = tr.xpath('./td[1][@style="text-align:left "]/a[@target="_blank"]/span/nobr/text()').extract_first()
                许可证号 = tr.xpath("./td[2]/text()").extract_first()
                项目链接 = a
                项目地址 = tr.xpath("./td[3]/span/nobr/text()").extract_first()
                开发企业名称 = tr.xpath("./td[4]/span/nobr/text()").extract_first()

                item = 不动产登记中心_商品房预售许可_item.不动产登记中心商品房预售许可Item()
                item.许可证号 = str.strip(许可证号)
                item.项目名称 = str.strip(项目名称)
                item.项目链接 = str.strip(项目链接)
                item.项目地址 = str.strip(项目地址)
                item.开发企业名称 = str.strip(开发企业名称)

                sql = "select id,项目链接  from 不动产登记中心_商品房预售许可 where 项目链接 = '" + 项目链接 + "'"
                row = self.db.find(sql=sql, limit=10, to_json=True)

                if row:
                    item.id = row[0]['id']
                    self.db.update_smart(table="不动产登记中心_商品房预售许可", data=item.to_dict, condition="id=" + str(row[0]['id']))
                else:
                    yield item



if __name__ == "__main__":
    JpTgxm().start()
