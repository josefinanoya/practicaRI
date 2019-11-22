# practicaRI
Miembros: Josefina Noya Vecino y Daniel Pena Docampo


## Pasos a seguir para ejecutar el proyecto

### Versiones de instalación
Vue.js -> @vue/cli 4.0.5

Java -> openjdk 1.8.0

Scrapy -> 1.7.4

Python -> 3.6.8

Elastic Search -> 7.4.0

### Comandos de ejecucción
Para realizar el crawling, en la carpeta /p1scrapy ejecutar "scrapy crawl phones".

Para crear el índice en ElasticSearch:

1.- Hacer un PUT a http://localhost:9200/phones con un header "Content-type application/json" con el siguiente body:

`{
	"settings" : {
		"analysis" : {
			"analyzer" : {
					"default" : {
						"tokenizer" : "standard",
						"filter" : ["my_ascii_folding"]
					}
				},
				"filter" : {
					"my_ascii_folding" : {
						"type" : "asciifolding",
						"preserve_original" : "true"
					}
				}
			}
	}
}`
  
 2.- Hacer un PUT a http://localhost:9200/phones/_mapping con un header "Content-type application/json" con el siguiente body:
	
`{
	"properties": {
            "url": {
                "type": "text",
                "index": "false"
            },
            "title": {
                "type": "text",
                "index": "true"
            },
            "image": {
                "type": "text",
                "index": "true"
            },
            "brand": {
                "type": "keyword",
                "index": "true"
            },
            "model": {
                "type": "text",
                "index": "true"
            },
            "warranty": {
                "type": "text",
                "index": "true"
            },
            "color": {
                "type": "keyword",
                "index": "true"
            },
            "intMemory": {
                "type": "keyword",
                "index": "true"
            },
            "extMemory": {
                "type": "text",
                "index": "true"
            },
            "ram": {
                "type": "keyword",
                "index": "true"
            },
            "battery": {
                "type": "integer",
                "index": "true"
            },
            "os": {
                "type": "text",
                "index": "true"
            },
            "osVersion": {
                "type": "text",
                "index": "true"
            },
            "processor": {
                "type": "text",
                "index": "true"
            },
            "procVelocity": {
                "type": "text",
                "index": "true"
            },
            "screenSize": {
                "type": "float",
                "index": "true"
            },
            "screenTech": {
                "type": "text",
                "index": "true"
            },
            "screenRes": {
                "type": "text",
                "index": "true"
            },
            "backCamNum": {
                "type": "keyword",
                "index": "true"
            },
            "backCamRes": {
                "type": "keyword",
                "index": "true"
            },
            "frontCamNum": {
                "type": "keyword",
                "index": "true"
            },
            "frontCamRes": {
                "type": "keyword",
                "index": "true"
            },
            "vidRecord": {
                "type": "text",
                "index": "true"
            },
            "fingerprint": {
                "type": "text",
                "index": "true"
            },
            "price": {
                "type": "float",
                "index": "true"
            }
            
	}	
}`

Para indexar los datos, en la carpeta /p1scrapy se ejecuta "python index_through.py".

Ejecutar "sudo systemctl start elasticsearch.service" para arrancar ElasticSearch.

En la carpeta /vue-app ejecutar "npm run serve".

En la carpeta /back/phone-searcher ejecutar "mvn spring-boot:run".
