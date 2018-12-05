document.addEventListener("DOMContentLoaded", function(ev) {

	const vmSearch = new Vue({
		el: '#vm-search',
		data: {
			inputValue: ''
		},
		methods: {
			search: function (searchParam) {
				let usparams = new URLSearchParams();
				usparams.append("search_name", searchParam);
				let queryUrl = "/example_ecsite/webapi/item/search_name?" + usparams.toString();
				axios
					.get(queryUrl)
					.then(response => (vmItems.items = response.data.result))
					;

				vmItems.showInfo = "検索：" + searchParam;
			}
		}
	});

	const vmKinds = new Vue({
		el: '#vm-kinds',
			data: {
			kinds: []
			},
		mounted: function() {
			let queryUrl = "/example_ecsite/webapi/kind/all";
			axios
				.get(queryUrl)
				.then(response => (this.kinds = response.data.result))
				;
		},
		methods: {
			kindClick:function(ev, kindId){
				let usparams = new URLSearchParams();
				usparams.append("kind_id", kindId);
				let queryUrl = "/example_ecsite/webapi/item/kind?" + usparams.toString();
				axios
					.get(queryUrl)
					.then(response => (vmItems.items = response.data.result))
					;

				for(let kind of vmKinds.kinds){
					if(kind.kindId === kindId){
						vmItems.showInfo = "分類：" + kind.kindName;
						break;
					}
				}
			}
		}
	});

	const vmCert = new Vue({
		el: '#vm-cert',
		data: {
			items: []
		},
		methods:{
			itemAdd:function(addItem){
				let targetIndex=-1;
				for(let i=0; i<this.items.length; i++){
					if(this.items[i].itemId === addItem.itemId){
						targetIndex = i;
						break;
					}
				}
				if(targetIndex === -1){
					let addItemCopy = Object.assign({}, addItem);
					addItemCopy.count = 1;
					this.items.push(addItemCopy);
				}else{
					this.items[targetIndex].count++;
				}
			},
			bill:function(ev){
				let confirmResult = window.confirm("購入します。\nよろしいですか？");
				if(confirmResult){
					this.items = [];
					setTimeout(function(){alert("購入しました。");}, 500);
				}
			},
			itemPlus:function(ev, index){
				this.items[index].count++;
			},
			itemMinus:function(ev, index){
				if(this.items[index].count <= 1){
					this.items.splice(index, 1);
				}else{
					this.items[index].count--;
				}
			}
		},
		computed:{
			sumCount:function(){
				let resultCount = 0;
				for(let item of this.items){
					resultCount += item.count;
				}
				return resultCount;
			},
			sumPrice:function(){
				let resultPrice = 0;
				for(let item of this.items){
					resultPrice += item.itemPrice * item.count;
				}
				return resultPrice;
			}
		}
	});

	const vmItems = new Vue({
		el: '#vm-items',
		data: {
			showInfo:"",
			items: []
		},
		methods: {
			itemClick:function(ev, item){
				vmCert.itemAdd(item);
			}
		},
		mounted: function() {
		}
	});

	vmSearch.search("");
});
