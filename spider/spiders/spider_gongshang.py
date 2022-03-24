# -*- coding: utf-8 -*-
"""
Created on 2022-03-17 17:39:16
---------
@summary:
---------
@author: Administrator
"""

import feapder
#from fake_useragent import UserAgent
from feapder.utils.webdriver import WebDriver
from items import gongshang_base_item


class SpiderGongshang(feapder.AirSpider):


    def start_requests(self):
        url = 'http://www.gsxt.gov.cn/index.html'
        yield feapder.Request(url,render=True, callback=self.parser_detail)

    def parser_detail(self, request, response):
        browser: WebDriver = response.browser

        browser.implicitly_wait(30)  # 隐性等待，最长等30秒
        browser.find_element(value="keyword").send_keys("湖北英格锐盛科技有限公司")
        browser.find_element(value="btn_query").click()
        # TODO 打码
        response.text = browser.page_source

        # response也是可以正常使用的
        href = response.xpath('//div[@id="advs"]/div/div[2]/a[@class="search_list_item db"]/@href').extract_first()
        #name = response.xpath('//div[@id="advs"]/div/div[2]/a[@class="search_list_item db"]/h1/text()').extract_first()
        #stat = response.xpath('//div[@id="advs"]/div/div[2]/a[@class="search_list_item db"]/div[1]/span/text').extract_first()


        yield feapder.Request(url=href ,render=True, callback=self.parser_list)

    def parser_list(self, request, response):
        print(response)

        #统一社会信用代码 = response.xpath('/html/body/div[5]/div[4]/div[2]/div[30]/div[4]/div[2]/span[1]/span/text()').extract_first()
        #注册号 = response.xpath('/html/body/div[5]/div[4]/div[2]/div[30]/div[4]/div[2]/span[2]/span/text()').extract_first()
        #登记机关 = response.xpath('/html/body/div[5]/div[4]/div[2]/div[30]/div[4]/div[2]/span[4]/span/text()').extract_first()
        #法定代表人 = response.xpath('/html/body/div[5]/div[4]/div[2]/div[30]/div[4]/div[2]/span[3]/span/text()').extract_first()
        #成立日期 = response.xpath('/html/body/div[5]/div[4]/div[2]/div[30]/div[4]/div[2]/span[5]/span/text()').extract_first()

        #企业名称 = response.xpath('/html/body/div[5]/div[4]/div[2]/div[30]/div[4]/div[2]/div/h1/text()').extract_first()
        #登记状态 = response.xpath('/html/body/div[5]/div[4]/div[2]/div[30]/div[4]/div[2]/div/span[1]/text()').extract_first()


        统一社会信用代码 = response.xpath('//*[@id="primaryInfo"]/div/div[2]/dl[1]/dd/text()').extract_first()
        企业名称 = response.xpath('//*[@id="primaryInfo"]/div/div[2]/dl[2]/dd/text()').extract_first()
        注册号 = response.xpath('//*[@id="primaryInfo"]/div/div[2]/dl[3]/dd/text()').extract_first()
        法定代表人 = response.xpath('//*[@id="primaryInfo"]/div/div[2]/dl[4]/dd/text()').extract_first()
        类型 = response.xpath('//*[@id="primaryInfo"]/div/div[2]/dl[5]/dd/text()').extract_first()
        成立日期 = response.xpath('//*[@id="primaryInfo"]/div/div[2]/dl[6]/dd/text()').extract_first()
        注册资本= response.xpath('//*[@id="primaryInfo"]/div/div[2]/dl[7]/dd/text()').extract_first()
        核准日期 = response.xpath('//*[@id="primaryInfo"]/div/div[2]/dl[8]/dd/text()').extract_first()
        营业期限自 = response.xpath('//*[@id="primaryInfo"]/div/div[2]/dl[9]/dd/text()').extract_first()
        营业期限至 = response.xpath('//*[@id="primaryInfo"]/div/div[2]/dl[10]/dd/text()').extract_first()
        登记机关 = response.xpath('//*[@id="primaryInfo"]/div/div[2]/dl[11]/dd/text()').extract_first()
        登记状态 = response.xpath('//*[@id="primaryInfo"]/div/div[2]/dl[12]/dd/text()').extract_first()
        住所 = response.xpath('//*[@id="primaryInfo"]/div/div[2]/dl[13]/dd/text()').extract_first()
        经营范围 = response.xpath('//*[@id="primaryInfo"]/div/div[2]/dl[14]/dd/text()').extract_first()

        # TODO 入库
        item = gongshang_base_item.GongshangBaseItem()
        item.企业名称 = str.strip(企业名称)
        item.统一社会信用代码 = str.strip(统一社会信用代码)
        item.法定代表人 = str.strip(法定代表人)
        item.类型 = str.strip(类型)
        item.成立日期 = str.strip(成立日期)
        item.注册资本 = str.strip(注册资本)
        item.核准日期 = str.strip(核准日期)
        item.营业期限自 = str.strip(营业期限自)
        item.营业期限至 = str.strip(营业期限至)
        item.登记机关 = str.strip(登记机关)
        item.登记状态 = str.strip(登记状态)
        item.住所 = str.strip(住所)
        item.经营范围 = str.strip(经营范围)
        item.注册号 = str.strip(注册号)

        yield item


if __name__ == "__main__":
    SpiderGongshang().start()