import scrapy
from scrapy.spiders import CrawlSpider, Rule
from scrapy.linkextractors import LinkExtractor
from ..items import PhoneItem

class ClothingSpider(CrawlSpider):
    name = "phones"
    allowed_domains = ['worten.es']
    start_urls = ['https://www.worten.es/productos/moviles-smartphones/smartphones']

    # Aurora, Bleen, cristal --> AZUL
    colors = ["Negro", "Azul", "Blanco", "Dorado", "Gris", "Plata", "Rojo", "Amarillo", "Coral", "Rosa", "Verde", "Turquesa", "Marron"]
    
    rules = ( 
        Rule(LinkExtractor(restrict_xpaths=('//div[@class="w-products-list__wrapper"]',)), callback = 'parse_item', follow=True),
        Rule(LinkExtractor(restrict_xpaths=('//ul[@class="pagination text-center"]',)), callback = 'parse_item', follow=True),
    )


    def getData(self, response, pString, spanString, column):

        finished = False
        if column == 0:
            searchItem = "//div[@class='w-product-details__column w-product-details__moreinfo show-for-medium']"

        else:
            searchItem = "//div[@class='w-product-details__column']"

        indexlu = 1
        indexli = 1

        for i in range(0, 30):

            if response.xpath(searchItem + "/p[" + str(indexlu) + "]/text()").extract_first() == pString:
                finished = True
                break

            else:
                indexlu += 1

        if not finished:
            return ""

        finished = False

        for i in range(0, 15):

            if response.xpath(searchItem + "/ul[" + str(indexlu) + "]/li[" + str(indexli) + "]/span[1]/text()").extract_first() == spanString:
                finished = True
                break

            else:
                indexli += 1

        if finished:

            print("**************************************************************************************************\n")
            print(response.xpath(searchItem + "/ul[" + str(indexlu) + "]/li[" + str(indexli) + "]/span[2]/text()").extract_first())

            return response.xpath(searchItem + "/ul[" + str(indexlu) + "]/li[" + str(indexli) + "]/span[2]/text()").extract_first()

        else:
            print("******ESTE ELEMENTO NO TIENE LA POTENCIA DE LA BATERÍA********")
            return ""


    def getImages(self, response):

        startUrl = "https://www.worten.es"
        i = 1
        images = []

        while True:

            endUrl = (response.xpath("//div[@class='swiper-wrapper']/div[" + str(i) + "]/img/@src").extract_first())
            print(endUrl)

            if endUrl is None:
                return images

            else:
                images.append(startUrl + endUrl)

            i += 1


    def prizeToFloat(self, prize):

        if prize is not None:
            return float(prize.replace(',','.'))

        return None


    def quitPoint(self, battery):

        bfinal = battery.replace('.', '')
        return int(bfinal)

    def parseBattery(self, battery):

        if battery == "":
            return ""

        b1 = battery.split(' ')

        if len(b1) < 6:

            if len(b1) == 1:

                if b1[0][-1:] == "h":
                    b2 = b1[0].split("m")[0]
                    return self.quitPoint(b2)

                return self.quitPoint(b1[0])

            if len(b1) == 3:

                if b1[2][-1:] == "h":
                    return self.quitPoint(b1[1])

                return self.quitPoint(b1[0])

            return self.quitPoint(b1[0])

        return -1.0

    def parseScreen(self, screenSize):

        if screenSize == "":
            return ""

        screenSize = screenSize.replace("''", "\"")

        screen = screenSize.split('"')[0]

        finalscreen = screen.replace(',', '.')
        try:

            return float(finalscreen)

        except ValueError:

            print("************************************************************************************************************************")
            print(screen)
            print("*************************************************************************************************************************")
            self.logger.warning("ESTA SCREEEN SIZE NO SE PUEDE PASAR A FLOAT")
            return 0.0


    def parseColors(self, color):

        c2 = ""
        color1 = color.split(" ")
        c1 = color1[0].lower()

        if len(color1) > 1:
            c2 = color1[1].lower()

        for basecolor in self.colors:

            if c1.startswith(basecolor.lower()) or c2.startswith(basecolor.lower()):
                return basecolor

        return color

    def parseResolution(self, resolution):

        splitRes = resolution.upper().split("+")
        finalRes = ""
        i = 0

        for resfrag in splitRes:

            if i > 0:
                finalRes = finalRes + " + "

            resNumber = resfrag.split("M")

            if len(resNumber) > 1:
                if resNumber[0].strip()[0:1] == "0":
                    finalRes = finalRes + resNumber[0].strip() + " MP"

                else:
                    finalRes = finalRes + resNumber[0].split(".")[0].strip() + " MP"


            else:
                finalRes = finalRes + resNumber[0].strip()

            i += 1

        return finalRes


    def parse_item(self, response):

        phone = PhoneItem()
        phone['url'] = response.url
        phone['title'] = response.xpath("//div[@class='w-product__desc show-for-medium']/span/text()").extract_first()
        phone['image'] = self.getImages(response)
        phone['brand'] = self.getData(response, "Referencias", "Marca", 0)
        phone['model'] = self.getData(response, "Referencias", "Modelo", 0)
        phone['warranty'] = self.getData(response, "Referencias", "Garantía", 0)
        phone['color'] = self.parseColors(self.getData(response, "Características físicas", "Color", 0))
        phone['intMemory'] = self.getData(response, "Memoria", "Memoria interna", 0)
        phone['extMemory'] = self.getData(response, "Memoria", "Memoria externa", 0)
        phone['ram'] = self.getData(response, "Memoria", "RAM", 0)
        phone['battery'] = self.parseBattery(self.getData(response, "Batería", "Potencia de la batería", 0))
        phone['os'] = self.getData(response, "Características específicas", "Sistema operativo", 1)
        phone['osVersion'] = self.getData(response, "Características específicas", "Versión del sistema operativo", 1)
        phone['processor'] = self.getData(response, "Características específicas", "Procesador", 1)
        phone['procVelocity'] = self.getData(response, "Características específicas", "Velocidad del procesador", 1)
        phone['screenSize'] = self.parseScreen(self.getData(response, "Pantalla", "Diagonal de la pantalla", 1))
        phone['screenTech'] = self.getData(response, "Pantalla", "Tecnología de la pantalla", 1)
        phone['screenRes'] = self.getData(response, "Pantalla", "Resolución de la pantalla", 1)
        phone['backCamNum'] = self.getData(response, "Cámara", "Número de cámaras traseras", 1)
        phone['backCamRes'] = self.parseResolution(self.getData(response, "Cámara", "Resolución de la cámara trasera", 1))
        phone['frontCamNum'] = self.getData(response, "Cámara", "Número de cámaras frontales", 1)
        phone['frontCamRes'] = self.parseResolution(self.getData(response, "Cámara", "Resolución de la cámara frontal", 1))
        phone['vidRecord'] = self.getData(response, "Cámara", "Grabación de vídeo", 1)
        phone['fingerprint'] = self.getData(response, "Sensores", "Sensor de huellas dactilares", 1)
        phone['price'] = self.prizeToFloat(response.xpath("//div[@class='w-product__price iss-product-price-container']/span/@content").extract_first())


        yield phone

    
