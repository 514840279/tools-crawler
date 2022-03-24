# -*- coding: utf-8 -*-
"""
Created on 2022-03-16 14:19:55
---------
@summary:
---------
@author: b_邓波
"""
import requests
import re
import js2py
import execjs
import randomUserAgent
import feapder
from feapder.utils import metrics
import time
from items import gongshang_list_item


def get_cookies(headers):
    # headers = {
    #     'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36',
    # }
    session = requests.session()
    session.headers = headers

    index_url = 'http://www.gsxt.gov.cn/index.html'
    response = session.get(index_url)
    # <script>document.cookie=('_')+('_')+('j')+('s')+('l')+('_')+('c')+('l')+('e')+('a')+('r')+('a')+('n')+('c')+('e')+('=')+(-~0+'')+(1+5+'')+(-~(3)+'')+([2]*(3)+'')+(-~{}+'')+(-~[]+'')+(1+8+'')+(1+2+'')+(1+6+'')+((1<<3)+'')+('.')+(-~1+'')+((2)*[2]+'')+('|')+('-')+(-~0+'')+('|')+(+!+[]*2+'')+('K')+(~~[]+'')+(~~''+'')+('x')+(1+8+'')+(1+[0]-(1)+'')+('t')+('K')+('k')+('s')+('y')+('I')+('D')+('m')+('r')+('r')+('a')+('n')+((2^1)+'')+('Z')+('d')+(1+8+'')+('h')+('j')+('k')+('Y')+('%')+(3+'')+('D')+(';')+('m')+('a')+('x')+('-')+('a')+('g')+('e')+('=')+((1+[2]>>2)+'')+([2]*(3)+'')+(~~false+'')+(~~''+'')+(';')+('p')+('a')+('t')+('h')+('=')+('/');location.href=location.pathname+location.search</script>
    # print(session.get(index_url).content.decode(('utf-8')))
    js1 = re.findall('<script>(.+?)</script>', response.content.decode())[0].replace('document.cookie=', '').replace('location.href=location.pathname+location.search', '')
    context = js2py.EvalJs()
    context.execute('cookies2 =' + js1)
    cookies = context.cookies2.split(';')[0].split('=')
    session.cookies.set(cookies[0], cookies[1])  # 到此拿到第两个cookie
    # print(cookies)
    # print(session.cookies)
    # print("-----------------------------------------------------------------")

    # print(session.get(index_url).content.decode(('utf-8')))

    # 添加jsdom实现浏览器上下文
    js2 = '''const jsdom = require("jsdom");const {JSDOM} = jsdom;const dom = new JSDOM();window = dom.window;document = window.document;location = new Array();''' + re.findall('<script>(.+?)</script>', session.get(index_url).content.decode('utf-8'))[0]
    # print(js2)

    # 正则获取document['cookie']，由于每次个数不一样我们取最后一个
    cookies2_1 = re.findall(r"document\[.*?\]=(.*?)location", js2, re.S)[-1]
    # print(cookies2_1)

    # 将document['cookie']内容返回给go函数
    js3 = re.sub("};go", "return " + cookies2_1 + "};go", js2, 1)
    # print(js3)

    # 获取调用go函数时里面的参数
    request = re.findall(r"go\({(.*?)}\)", js3, re.S)[-1]

    # 通过python修改js生成一个request方法
    final_js = js3 + "\nfunction request() {return go({" + request + "})}"

    # js调用request方法返回cookie并将新的__jsl_clearance塞给session中
    # print("--"+final_js)

    print("----------1111-----------" * 12)
    # cookies3 =execjs.compile(final_js,cwd='D:\\tmp')
    cookies3 = execjs.compile(final_js, cwd='C:\\Program Files\\nodejs\\node_modules').call('request').split(';')[0].split('=')
    session.cookies.set(cookies3[0], cookies3[1])
    # print(cookies3)
    # print(requests.utils.dict_from_cookiejar(session.cookies))

    session.get(index_url)
    cookies = requests.utils.dict_from_cookiejar(session.cookies)
    # print(cookies)
    return cookies
    
class SpiderTest(feapder.AirSpider):

    def start_requests(self):
            url = 'http://www.gsxt.gov.cn/affiche-query-area-info-paperall.html?noticeType=21&areaid=100000&noticeTitle=&regOrg=110000'
            data = {
                # 'draw': '0',
                'start': '1',
                'length': '10'
            }
            headers = randomUserAgent.get_request_header()
            metrics.emit_counter("headers", count=1, classify="生成headers")
            cookies = get_cookies(headers)
            metrics.emit_counter("cookies", count=1, classify="生成cookies")
            # cookies ={'__jsl_clearance': '1647309459.37|0|sna1K2wv67Ux%2FLhdFOPr6BQRGck%3D', 'HttpOnly': None, 'JSESSIONID': 'b4181ab16568761343ab2fcf5d21', 'SECTOKEN': '7349790856915453849', '__jsluid_h': 'dd78ca288c519a5e0877a2e6f911873d', 'tlb_cookie': 'S172.16.12.68'}
            #response = requests.post(url, data=data, cookies=cookies, headers=headers)
            #print(response.status_code)
            # print(response.content.decode())
            #text = response.content.decode()
            #return text

            #yield feapder.Request("https://segmentfault.com/a/1190000011545291")
            for i in range(1, 2):
                yield feapder.Request(url=url,data=data,cookies=cookies, headers=headers,random_user_agent=False,replace_entities=False)
                time.sleep(2)

    def parse(self, request, response):
        metrics.emit_counter("success", count=1, classify="请求完成")
        #feapder.response.encoding_errors="ignore"
        response.encoding_errors = "ignore"
        data = response.json

        for  item in data['data'] :

            list_item  = gongshang_list_item.GongshangListItem()
            list_item.notice_id = item['noticeId']
            list_item.notice_date = item['noticeDate']
            list_item.notice_type = item['noticeType']
            list_item.datafrom = item['datafrom']
            list_item.ent_name = item['entName']
            list_item.jud_auth = item['judAuth']
            list_item.jud_auth__c_n = item['judAuth_CN']
            list_item.notice_content = item['noticeContent']
            list_item.jud_date = item['judDate']
            list_item.last_modified_time = item['lastModifiedTime']
            list_item.node_num = item['nodeNum']
            list_item.notice_n_o = item['noticeNO']
            list_item.notice_title = item['noticeTitle']
            list_item.notice_type = item['noticeType']
            list_item.simple_cancel_url = item['simpleCancelUrl']

            yield list_item



if __name__ == "__main__":
    SpiderTest().start()