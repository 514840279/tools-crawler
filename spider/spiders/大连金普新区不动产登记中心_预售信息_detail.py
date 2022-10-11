# -*- coding: utf-8 -*-
"""
Created on 2022-04-05 13:44:36
---------
@summary:
---------
@author: wth
"""

import feapder
from feapder.db.mysqldb import MysqlDB
from items import 不动产登记中心_商品房预售许可_item, 不动产登记中心_商品房预售房源_item
from urllib.parse import quote


class YsTgxmDetail(feapder.AirSpider):
    db = MysqlDB()

    def start_requests(self):

        sql = "select id,项目链接,项目名称,项目地址,开发企业名称,许可证号  from 不动产登记中心_商品房预售许可 where delete_flag = 0"
        rows = self.db.find(sql=sql, limit=15000, to_json=True)
        try:
            for row in rows:
                url = row['项目链接']
                url_encode = quote(url, safe='/:?=&', encoding='GBK', errors=None)
                print(url,url_encode)
                yield feapder.Request(url_encode, row=row)
        except:
            pass

    def parse(self, request, response):
        row = request.row
        item = 不动产登记中心_商品房预售许可_item.不动产登记中心商品房预售许可Item()
        content = response.xpath('//form[@name="ysxkzForm"]')[0]
        if content is not None:
            item.id = row['id']
            item.许可证号 = row['许可证号']
            item.项目名称 = row['项目名称']
            item.项目链接 = row['项目链接']
            item.项目地址 = row['项目地址']
            item.开发企业名称 = row['开发企业名称']

            item.可售总面积 = content.xpath('./table/tr[5]/td[2]/text()').extract_first()
            item.发证机关 = content.xpath('./table/tr[6]/td[2]/text()').extract_first()
            item.发证日期 = content.xpath('./table/tr[7]/td[2]/text()').extract_first()
            item.资质证书号 = content.xpath('./table/tr[8]/td[2]/text()').extract_first()
            item.营业执照号 = content.xpath('./table/tr[9]/td[2]/text()').extract_first()

            item.delete_flag = 1

            self.db.update_smart(table="不动产登记中心_商品房预售许可", data=item.to_dict, condition="id=" + str(row['id']))

        rowform = response.xpath('//form[@name="ysxkzForm"]')[1]
        if rowform is not None:
            trs = rowform.xpath('.//tr')

            for tr in trs:
                工程链接 = tr.xpath('./td[5]/a/@href').extract_first()
                if 工程链接:
                    rowdata = 不动产登记中心_商品房预售房源_item.不动产登记中心商品房预售房源Item()

                    rowdata.项目id = row['id']
                    rowdata.工程链接 = 工程链接
                    rowdata.工程楼号 = str.strip(tr.xpath('./td[2]/span/nobr/text()').extract_first())
                    rowdata.建筑面积 = str.strip(tr.xpath('./td[3]/span/nobr/text()').extract_first())
                    rowdata.坐落 = str.strip(tr.xpath('./td[4]/span/nobr/text()').extract_first())

                    rowdata.lid = 工程链接.replace('http://www.fczw.cn/lpb.xhtml?method=queryLpb&lid=', '').replace("http://fcjyw.dlhitech.gov.cn/lpb.xhtml?method=queryLpb&",'')
                    ab = rowdata.lid[0:rowdata.lid.find('&')]

                    rowdata.xmid = rowdata.lid.replace(ab+'&xmid=', '')
                    rowdata.lid = ab

                    yield rowdata
        sql = "update 不动产登记中心_商品房预售许可  set delete_flag=2 where delete_flag = 1 and id =%s" % row['id']

        self.db.update(sql)


if __name__ == "__main__":
    YsTgxmDetail(thread_count=5).start()
