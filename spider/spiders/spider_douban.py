import feapder
from items import booklist_item


class ListSpider(feapder.AirSpider):
    def start_requests(self):
        self.count = 1
        yield feapder.Request("https://book.douban.com/top250")  #

    def parse(self, request, response):
        print(response.content)
        infos = response.xpath("//tr[@class='item']")
        for info in infos:
            #  书名
            name = info.xpath('td/div/a/@title').extract_first()

            # 书的链接地址
            book_url = info.xpath('td/div/a/@href').extract_first()
            # 获取的是书本的基本信息,有作者和出版社,和出版日期...
            book_infos = info.xpath('td/p/text()').extract_first()
            # 作者
            author = book_infos.split('/')[0]
            # 出版社
            publisher = book_infos.split('/')[-3]
            # 出版日期
            date = book_infos.split('/')[-2]
            # 价格
            price = book_infos.split('/')[-1]
            # 书本的评分
            rate = info.xpath('td/div/span[2]/text()').extract_first()
            # 下面的评论
            comments = info.xpath('td/p/span/text()').extract()
            # 这里单行的if语句是:如果comments的长度不为0时,则把comments的第1个元素给comment,否则就把"空"赋值给comment
            comment = comments[0] if len(comments) != 0 else "NULL"
            # print((name ,book_url , book_infos , author , publisher , date , price , rate ,  comment))
            print((name))

            list_item = booklist_item.BooklistItem()  # Load database
            list_item.name = name
            list_item.book_url = book_url
            list_item.book_infos = book_infos
            list_item.author = author
            list_item.publisher = publisher
            list_item.date = date
            list_item.price = price
            list_item.rate = rate
            list_item.comment = comment

            yield list_item

        if infos.xpath("//span[@class='next']/a"):
            next_pag = infos.xpath("//span[@class='next']/a/@href").extract_first()
            self.count += 1
            yield feapder.Request(url=next_pag)  # When the next page appears, go directly to


if __name__ == "__main__":
    ListSpider().start()
    # 整体逻辑是： 获取当前页所有相关信息，并获取下一页链接，持续循环
