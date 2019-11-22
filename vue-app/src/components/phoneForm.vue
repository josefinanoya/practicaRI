<template>
	<div class="s003">
		<div v-if="loading" class="lds-ring"><div></div><div></div><div></div><div></div></div>
      <form v-if="!loading">
		<fieldset><legend>Busca tu móvil ideal</legend></fieldset>
		<div class="inner-form">
          <div class="input-field first-wrap">
            <div class="input-select">
              <select v-model="phone.brand">
				<option value="">Marca</option>
				<option v-for="(brand) in brands" :key="brand.text">
					{{brand.text}}
				</option>
			</select>
			</div>
          </div>
		<div class="input-field first-wrap">
            <div class="input-select">
              <select v-model="phone.color">
				<option value="">Color</option>
				<option v-for="(color) in colors" :key="color.text">
					{{color.text}}
				</option>
			</select>
			</div>
		</div>
		<div class="input-field first-wrap">
            <div class="input-select">
              <select @change ="getPhoneValue($event)">
				<option value="" >Batería</option>
				<option v-for="(battery) in batteries" :key="battery">
					>= {{battery}} mAh
				</option>
			</select>
			</div>
		</div>
		<div class="input-field first-wrap">
            <div class="input-select">
              <select v-model="phone.intMemory">
				<option value="">Memoria Interna</option>
				<option v-for="(intMemory) in intMemories" :key="intMemory.text">
					{{intMemory.text}}
				</option>
			</select>
			</div>
        </div>
		<div class="input-field first-wrap">
            <div class="input-select">
              <select v-model="phone.ram">
				<option value="">RAM</option>
				<option v-for="(RAM) in RAMs" :key="RAM.text">
					{{RAM.text}}
				</option>
			</select>
			</div>
		</div>
		<div class="input-field first-wrap">
            <div class="input-select">
              <select @change ="getScreenSizeValue($event)">
				<option value="">Tamaño de pantalla</option>
				<option v-for="(screenSize) in screenSizes" :key="screenSize">
					>= {{screenSize}} ''
				</option>
			</select>
			</div>
        </div>
		<div class="input-field first-wrap">
            <div class="input-select">
              <select @change ="getPriceValue($event)">
				<option value="">Precio</option>
				<option v-for="(price) in prices" :key="price">
					&lt;= {{price}} €
				</option>
			</select>
			</div>
		</div>
    </div>


	<div class="inner-form">
        <div class="input-field first-wrap">
            <div class="input-select">
              <select v-model="phone.frontCamNum" @change="setResolutions($event, 0)">
				<option value="">Cámaras frontales</option>
				<option v-for="(frontCamNum) in frontCamNums" :key="frontCamNum.text">
					{{frontCamNum.text}}
				</option>
			</select>
			</div>
        </div>
		<div class="input-field first-wrap">
            <div class="input-select">
              <select v-model="phone.backCamNum" @change="setResolutions($event, 1)">
				<option value="">Cámaras traseras</option>
				<option v-for="(backCamNum) in backCamNums" :key="backCamNum.text">
					{{backCamNum.text}}
				</option>
			</select>
			</div>
		</div>
		<div class="larger-wrap">
            <div class="input-select">
              <select v-model="phone.frontCamRes">
				<option value="">Resolución cámara frontal</option>
				<option v-for="(frontCamRes) in frontCamRess" :key="frontCamRes.text">
					{{frontCamRes.text}}
				</option>
			</select>
			</div>
		</div>
		<div class="larger-wrap">
            <div class="input-select">
              <select v-model="phone.backCamRes">
				<option value="">Resolución cámara trasera</option>
				<option v-for="(backCamRes) in backCamRess" :key="backCamRes.text">
					{{backCamRes.text}}
				</option>
			</select>
			</div>
        </div>
		<div class="input-field third-wrap">
            <button class="btn-search" type="button" @click = "search()">
              <svg class="svg-inline--fa fa-search fa-w-16" aria-hidden="true" data-prefix="fas" data-icon="search" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                <path fill="currentColor" d="M505 442.7L405.3 343c-4.5-4.5-10.6-7-17-7H372c27.6-35.3 44-79.7 44-128C416 93.1 322.9 0 208 0S0 93.1 0 208s93.1 208 208 208c48.3 0 92.7-16.4 128-44v16.3c0 6.4 2.5 12.5 7 17l99.7 99.7c9.4 9.4 24.6 9.4 33.9 0l28.3-28.3c9.4-9.4 9.4-24.6.1-34zM208 336c-70.7 0-128-57.2-128-128 0-70.7 57.2-128 128-128 70.7 0 128 57.2 128 128 0 70.7-57.2 128-128 128z"></path>
              </svg>
            </button>
		</div>
			
    </div>
	</form>

	<legend v-if="hidden" id="error-msg">No hay móviles que sigan estas características.</legend>
	<div class="cards">
		<div class="card card--1"  v-for="phone in phones" :key="phone.url">
			<a href="#popup" class="card_link" @click="selectPhone(phone)">
				<img v-if="phone.image" class="card__img" :src="showPic(phone.image)">
			</a>
			<div class="card__info">
				<span class="card__category"> {{phone.brand}}</span>
				<h3 class="card__title">{{phone.title}}</h3>
				<span class="card__by">Precio: <div class="card__author" title="author">{{phone.price}}</div></span>
			</div>
		</div>

		<span id="load-more" @click="loadMorePhones()">MOSTRAR MÁS</span>
		
	</div>
	
	

	<div class="popup" id="popup">
		<div class="popup-inner">
			<div class="popup__photo">
				<img v-if="selectedPhone.image" :src="showPic(selectedPhone.image)">
			</div>
			<div class="popup__text">
				<h1>{{selectedPhone.title}}</h1>
				<p v-if="selectedPhone.brand">Marca: {{selectedPhone.brand}}</p>
				<p v-if="selectedPhone.model">Modelo: {{selectedPhone.model}}</p>
				<p v-if="selectedPhone.color">Color: {{selectedPhone.color}}</p>
				<p v-if="selectedPhone.warranty">Garantía: {{selectedPhone.warranty}}</p>
				<p v-if="selectedPhone.intMemory">Memoria interna: {{selectedPhone.intMemory}}</p>
				<p v-if="selectedPhone.extMemory">Memoria externa: {{selectedPhone.extMemory}}</p>
				<p v-if="selectedPhone.ram">RAM: {{selectedPhone.ram}}</p>
				<p v-if="selectedPhone.battery">Batería: {{selectedPhone.battery}}</p>
				<p v-if="selectedPhone.os">Sistema operativo: {{selectedPhone.os}}</p>
				<p v-if="selectedPhone.osVersion">Versión del sistema operativo: {{selectedPhone.osVersion}}</p>
				<p v-if="selectedPhone.processor">Procesador: {{selectedPhone.processor}}</p>
				<p v-if="selectedPhone.procVelocity">Velocidad del procesador: {{selectedPhone.procVelocity}}</p>
				<p v-if="selectedPhone.screenSize">Tamaño de pantalla: {{selectedPhone.screenSize}}</p>
				<p v-if="selectedPhone.screenTech">Tecnología de pantalla: {{selectedPhone.screenTech}}</p>
				<p v-if="selectedPhone.screenRes">Resolución de pantalla: {{selectedPhone.screenRes}}</p>
				<p v-if="selectedPhone.backCamNum">Número de cámaras traseras: {{selectedPhone.backCamNum}}</p>
				<p v-if="selectedPhone.backCamRes">Resolución de cámara trasera: {{selectedPhone.backCamRes}}</p>
				<p v-if="selectedPhone.frontCamNum">Número de cámaras delanteras: {{selectedPhone.frontCamNum}}</p>
				<p v-if="selectedPhone.frontCamRes">Resolución de cámara delantera: {{selectedPhone.frontCamRes}}</p>
				<p v-if="selectedPhone.vidRecord">Velocidad de vídeo: {{selectedPhone.vidRecord}}</p>
				<p v-if="selectedPhone.fingerprint">Huella dactilar: {{selectedPhone.fingerprint}}</p>
				<p v-if="selectedPhone.price">Precio: {{selectedPhone.price}}</p>
			</div>
			<a class="popup__close" href="#" >X</a>
		</div>
	</div>

</div>
</template>

<script>

import axios from 'axios'

const HTTP = axios.create({
	baseURL: 'http://localhost:8090/'
})


	export default {
		data() {
			return {
				phone: {
					brand: "",
					color: "", 
					battery: "",
					intMemory: "",
					screenSize: "",
					price: "",
					ram: "",
					frontCamNum: "",
					backCamNum: "",
					frontCamRes: "",
					backCamRes: "",
					more: 1
				},
				prices: [200.00, 400.00, 600.00, 800.00, 1000.00, 1200.00, 1400.00, 1600.00],
				batteries: [1000, 2000, 3000, 4000, 5000, 6000],
				screenSizes: [3.0, 3.5, 4.0, 4.5, 5.0, 5.5, 6.0], 
				selectedPhone: {},
				phones: [],
				allBrands: [],
				allColors: [],
				allIntMemories: [],
				allFrontCamNums: [],
				allBackCamNums: [],
				allFrontCamRes: [],
				allBackCamRes: [],
				allRAMs: [],
				error: null,
				hidden: false,
				loading: true,
				listFrontCamRes: [],
				listBackCamRes: [],

				
			}
		},
		computed: {
			brands() {
				return this.allBrands.map(brand => {
					return {
					text: brand,
					value: brand
					}
				})
			},
			colors() {
				return this.allColors.map(color => {
					return {
					text: color,
					value: color
					}
				})
			},
			intMemories() {
				return this.allIntMemories.map(intMemory => {
					return {
					text: intMemory,
					value: intMemory
					}
				})
			},
			RAMs() {
				return this.allRAMs.map(RAM => {
					return {
					text: RAM,
					value: RAM
					}
				})
			},
			frontCamNums() {
				return this.allFrontCamNums.map(cam => {
					return {
					text: cam,
					value: cam
					}
				})
			},
			backCamNums() {
				return this.allBackCamNums.map(cam => {
					return {
					text: cam,
					value: cam
					}
				})
			},
			frontCamRess() {
				return this.listFrontCamRes.map(cam => {
					return {
					text: cam,
					value: cam
					}
				})
			},
			backCamRess() {
				return this.listBackCamRes.map(cam => {
					return {
					text: cam,
					value: cam
					}
				})
			},
			


		},
		created() {
			this.getBrands()
			this.getColors()
			this.getIntMemories()
			this.getRAMs()
			this.getFrontCamNums()
			this.getBackCamNums()
			this.getFrontCamRes()
			this.getBackCamRes()
		},
		methods: {
			search(){
				this.phone.more = 1
				HTTP.post('phones', this.phone)
				.then(response => {
					this.phones = response.data
					this.checkPhones(response)
				})
				.catch(err => this.error = err.message)
			},
			loadMorePhones() {
				this.phone.more += 1
				HTTP.post('phones/more', this.phone)
				.then(response => {
					this.phones = response.data
					this.checkPhones(response)
				})
				.catch(err => this.error = err.message)
			},
			selectPhone(phone) {
				this.selectedPhone = phone
			},
			showPic(image){
				return image.split(",")[0].slice(2,-1)
			},
			showPhones() {

				var div = document.getElementById('phoneinfo');

				this.phones.forEach(phone => div.innerHTML += phone.title);
			},
			checkPhones(response) {
				if (response.data.length === 0)
					this.hidden = true
				else
					this.hidden = false
				var mySpan = document.getElementById('load-more');
				if (response.data.length === this.phone.more * 50)
					mySpan.style.visibility = "visible";
				else
					mySpan.style.visibility = "hidden";
			},
			getBrands() {
				HTTP.get('phones/brands')
				.then(response => {
					this.allBrands = response.data
					this.loading=false
				})
				.catch(err => this.error = err.message)
			},
			getColors() {
				HTTP.get('phones/colors')
				.then(response => this.allColors = response.data)
				.catch(err => this.error = err.message)
			},
			getIntMemories() {
				HTTP.get('phones/intMemories')
				.then(response => this.allIntMemories = response.data)
				.catch(err => this.error = err.message)
			},
			getRAMs() {
				HTTP.get('phones/RAMs')
				.then(response => this.allRAMs = response.data)
				.catch(err => this.error = err.message)
			},
			getFrontCamNums() {
				HTTP.get('phones/frontCamNums')
				.then(response => this.allFrontCamNums = response.data)
				.catch(err => this.error = err.message)
			},
			getBackCamNums() {
				HTTP.get('phones/backCamNums')
				.then(response => this.allBackCamNums = response.data)
				.catch(err => this.error = err.message)
			},
			getFrontCamRes() {
				HTTP.get('phones/frontCamRes')
				.then(response => {
					this.allFrontCamRes = response.data
					this.listFrontCamRes = response.data	
				})
				.catch(err => this.error = err.message)
			},
			getBackCamRes() {
				HTTP.get('phones/backCamRes')
				.then(response => {
					this.allBackCamRes = response.data
					this.listBackCamRes = response.data
				})
				.catch(err => this.error = err.message)
			},
			parseResolutions(value, size) {
				var resolutions
				var finalres = []
				if (value == 0)
					resolutions = this.allFrontCamRes
				else 
					resolutions = this.allBackCamRes
				
				resolutions.forEach(function(res){
					if (res.split("+").length == size){
						finalres.push(res)
					}
				})
				return finalres
					
			},
			setResolutions(event, value) {
				if (value == 0) {
					if (event.target.value == "")
						this.listFrontCamRes = this.allFrontCamRes
					else
						this.listFrontCamRes = this.parseResolutions(value, event.target.value)
				}
				else {
					if (event.target.value == "")
						this.listBackCamRes = this.allBackCamRes
					else
					this.listBackCamRes = this.parseResolutions(value, event.target.value)
				}
			},
			getPhoneValue(event) {
				this.phone.battery = event.target.value.split(" ")[1]
			},
			getScreenSizeValue(event) {
				this.phone.screenSize = event.target.value.split(" ")[1]
			},
			getPriceValue(event) {
				this.phone.price = event.target.value.split(" ")[1]
			},
		},
	}
	
</script>

<style>
@import '../css/styles.css';
</style>