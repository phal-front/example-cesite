<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>vue-example</title>
<script src="https://unpkg.com/vue@2.5.17/dist/vue.js"></script>
<script src="https://unpkg.com/axios/dist/axios.js"></script>
<script src="./index.js"></script>
<link rel="stylesheet" type="text/css" href="./index.css">
</head>
<body>
	<header>
		<p>フロントエンドはVue.js　バックエンドはJava(JAX-RS)で作成しています。</p>
	</header>
	<section class="content">
		<section class="content-left">
			<div id="vm-search">
				<input type="text" v-model="inputValue" />
				<button @click="search(inputValue)">search</button>
			</div>
			<div id="vm-kinds">
				<ol>
					<li v-for="kind in kinds">
						<a @click="kindClick($event, kind.kindId)">{{kind.kindId}} - {{kind.kindName}}</a>
					</li>
				</ol>
			</div>
		</section>
		<section class="content-center">
			<div id="vm-items">
				<div>{{showInfo}}</div>
				<ol>
					<li v-for="item in items">
						<a @click="itemClick($event, item)">{{ item.itemId }} - {{ item.itemName}} - {{ item.itemPrice }}円</a>
					</li>
				</ol>
			</div>
		</section>
		<section class="content-right">
			<div id="vm-cert">
				<div>合計数：{{sumCount}}円</div>
				<div>合計金額：{{sumPrice}}円</div>
				<div><button @click="bill($event)">購入</button></div>
				<ol>
					<li v-for="(item, index) in items">
						{{ item.itemId }} - {{ item.itemName}} - {{ item.itemPrice }} - {{ item.count }}
						<button @click="itemPlus($event, index)">+</button>
						<button @click="itemMinus($event, index)">-</button>
					</li>
				</ol>
			</div>
		</section>
	</section>
</body>
</html>
