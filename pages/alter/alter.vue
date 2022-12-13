<template>
	<view class="box">
		<view class="avatar">
			<view class="uploadBtn" @tap="chosePicsAndUpload" >
			  <u-icon name="plus" size="60" color="#aaa" v-if="urls==null"/>
			  <u-avatar :src="urls" size="110" v-else></u-avatar>
			</view>
		</view>
		<view class="user">
			<u-icon name="account-fill"  size="28"></u-icon><u-input v-model="user" class="title-input" placeholder="姓名"/>
			<u-icon name="order" size="28"></u-icon><u-input v-model="intr" class="title-input" placeholder="个人介绍"/>
			<u-icon name="性别"></u-icon>
			<view class="title-input2">
				<u-radio-group v-model="value">
					<u-radio 
						@change="radioChange" 
						v-for="(item, index) in list" :key="index" 
						:name="item.name"
						:disabled="item.disabled">
							{{item.name}}
					</u-radio>
				</u-radio-group>
			</view>					
		</view>
		<view class="btn">
			<button class="login" @click="submit">修改资料</button>
		</view>
	</view>
</template>

<script>
	import {Update,Upload} from "@/api/index/index.js"
	import {mapState} from "vuex"
	export default {
		data() {
			
			return {
				list: [
						{
							name: '男',
							disabled: false
						},
						{
							name: '女',
							disabled: false
						},
				],
				value: '',
				user:'',
				intr:'',
				se:'',
				urls:null,		
			}
		},
		onLoad(){
			if(this.sex==1){
				this.value='男'
			}
			if(this.sex==0){
				this.value='女'
			}
			if(this.introduce!==null){
				this.intr=this.introduce
			}
			if(this.username!==null){
				this.user=this.username
			}
			if(this.avatar!==null){
				this.urls=this.avatar
			}

		},
		methods: {
			chosePicsAndUpload(){
				let _this=this
				uni.chooseImage({
					count: 1,
					sizeType: ["compressed"], 
					sourceType: ["album", "camera"],
					success: (res) => {
						_this.urls = [res.tempFiles][0][0].path;
					},
				});
			},
			radioChange(e) {
				this.value=e
			},
			async submit() {
				let tempsex=null,tempname,tempintr,tempurl;
				if(this.value!==null){
					tempsex=this.value=='男'?1:0;
				}
				if(this.intr==''){
					tempintr=null
				}
				else{
					tempintr=this.intr
				}
				if(this.user==''){
					tempname=null
				}
				else{
					tempname=this.user
				}
				if(this.urls!=null&&this.urls.substr(0,4)!='http'){
					let imgurl=[{
						name:'fileList',
						uri:this.urls
					}];
					let imgresult=JSON.parse(await Upload(imgurl));
					this.urls= imgresult.data.imageUrlList[0]
				}
				let result=await Update(this.urls,this.id,tempintr,tempsex,tempname)
				console.log(result)
				if(result.code==200){
					
					let data={
						id:this.id,
						appKey:this.appKey,
						username:tempsex,
						password:null,
						createTime:"1664769422055",
						lastUpdateTime:"1664769422055",
						username:tempname,
						sex:tempsex,
						introduce:tempintr,
						avatar:this.urls,
						lastUpdateTime:new Date().getTime()
					}
					this.$store.dispatch("getUserList",data);
					uni.showToast({
						title:"修改成功",
						icon: "success",
						duration: 1000,
					})
				}	
			},
			async alter(){
				let result=await Update(this.avatar,this.userid,this.introduce,this.sex,this.username)
				let data={
					
				}
				if(result.code==200){
					this.$store.dispatch("getUserList",);
					
					uni.showToast({
						title:'修改成功',
						icon:'success',
						duration: 1000
					})	
					setTimeout(function(){
						uni.switchTab({
							url:"/pages/myself/myself"
						})
					},1000)
				}
			},

			
		},
		computed: {
			...mapState({
				id:state=>state.user.id,
				username:state=>state.user.username,
				sex:state=>state.user.sex,
				introduce:state=>state.user.introduce,
				avatar:state=>state.user.avatar,
			}),
		},
	}
</script>

<style lang="scss" scoped>
	/deep/ uni-button:after{
		border: 0;
	}
	.title-input{
		position: relative;
		top: -55rpx;
		left: 80rpx;
		width: 73%;
	}
	.title-input2{
		position: relative;
		top: -35rpx;
		left: 100rpx;
		width: 73%;
	}
	.active{
		box-sizing: border-box;
		padding: 5px;
		color: #fff;
		width: 600rpx;
		height: 100rpx;
		background-color:#218c64;
		margin: 30rpx 80rpx;
		border-radius: 20px;
		padding-left: 60rpx;
		border: 0;
		outline: none;
	}
	
	.box{
		position:absolute;
		top:0;
		left:0;
		right:0;
		bottom:0;
		.text{
			color: #000;
			text-align: center;
			font-size: 20rpx;
			padding: 100rpx;
		}
		
		.uploadBtn {
			margin-top: 250rpx;
			margin-left: 270rpx;
			width: 210rpx;
			height: 210rpx;
			background-color: #eee;
			border-radius: 100rpx;
			align:center;
			display: flex;
			flex-direction: column;
			flex-wrap: wrap;
			justify-content: center;
			align-items: center;
			align-content: center;
			
			.text {
				color: #000;
				font-size: 8rpx;
				margin-top: 20rpx;
			}
		}
		.user{
			margin-top: 50px;
			input {
				box-sizing: border-box;
				padding: 5px;
				width: 600rpx;
				height: 100rpx;
				background-color:#f8f8f8;
				margin: 30rpx auto;
				border-radius: 20px;
				padding-left: 60rpx;
			}
			.user-forget{
				float: right;
				padding-right:65rpx;
				color: #76c2a5;
			}
		}
		.btn{
			 margin-top:100rpx;
			 button{
				 width: 600rpx;
				 border-radius: 25px;
			 }
			 .login{
			    color:#49ad88;
				font-weight: 700;
				border:1px solid #ccc;
			 }
		}		 
	}	
</style>

