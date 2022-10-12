<template>
	<view class="box">
		<view class="text">
			SIGN IN
		</view>
		<view class="user">
			<input type="text" focus placeholder="用户名" v-model="username" adjustResize placeholder-style="color:#76c2a5"/>
			<input :type="type" placeholder="输入密码" v-model="password"  class="active"/>
			<i class="iconfont eyes" :class="Icon" @click="flag=!flag"></i>
			<view class="user-forget">
				<u>忘记密码?</u>
			</view>
		</view>
		<view class="btn">
			<button class="login" @click="login">登录</button>
			<button class="register">注册</button>
		</view>
	</view>
</template>

<script>
import loginVue from './login.vue'
import qs from 'qs'
	export default {
		data() {
			return {
				showtexteare:false,
				flag:false,
				username:"jtw",
				password:"123",
			}
		},
		methods: {
			login(){
				uni.request({
					url:"http://47.107.52.7:88/member/photo/user/login",
					method:'POST',
					header:{
						  "Accept": "application/json, text/plain, */*",
						  "Content-Type": "application/x-www-form-urlencoded",
						  "appId": "24d8ed2ab0444b048cbd5fcdde289109",
						  "appSecret": "300002f6abcaf485d4cb19de0695a0b049dc0"
					},
					data:{
						username:this.username,password:this.password
						
					},
					success:res=>{
						if(res.data.code==200){
							let result=qs.stringify(res.data.data);
							uni.showToast({
											title: "登陆成功",
											icon:'success',
											duration:1000,
							});
							setTimeout(function(){
								uni.navigateTo({
									url:'/pages/index/index?'+result,
								})
							},1000)
						}
						else if(res.data.code==500){
							uni.showToast({
											title: "用户名或密码错误",
											icon:'error',
											duration:1000,
							})
						}
						console.log(res);
					},
					fail:()=>{},
					complete:()=>{},
				})
				
				
			},
		},
		computed:{
			type(){
				return this.flag?"text":"password";
			},
			Icon(){
				return !this.flag?"icon-yanjing":"icon-yanjing1"
			}
		},
	
	}
</script>

<style lang="scss" scoped>
	@font-face {
	  font-family: 'iconfont';  /* Project id 3699526 */
	  src: url('//at.alicdn.com/t/c/font_3699526_msey3u4iufd.woff2?t=1665493269660') format('woff2'),
	       url('//at.alicdn.com/t/c/font_3699526_msey3u4iufd.woff?t=1665493269660') format('woff'),
	       url('//at.alicdn.com/t/c/font_3699526_msey3u4iufd.ttf?t=1665493269660') format('truetype');
	}
	.iconfont {
	  font-family: "iconfont" !important;
	  font-size: 16px;
	  font-style: normal;
	  -webkit-font-smoothing: antialiased;
	  -moz-osx-font-smoothing: grayscale;
	}
	
	.icon-yanjing:before {
	  content: "\e6cf";
	}
	
	.icon-yanjing1:before {
	  content: "\e6d0";
	}
	::-webkit-input-placeholder{
		color:#76c2a5;
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
	.eyes{
		position: absolute;
		top:535rpx;
		right: 130rpx;
		color: #76c2a5;
	}
	.box{
		background-color: #409971;
		position:absolute;
		top:0;
		left:0;
		right:0;
		bottom:0;
		.text{
			color: #fff;
			text-align: center;
			font-size: 20rpx;
			padding: 100rpx;
		}
			
		.user{
			input {
				box-sizing: border-box;
				padding: 5px;
				color: #fff;
				width: 600rpx;
				height: 100rpx;
				background-color:#218c64;
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
			 margin-top:300rpx;
			 button{
				 width: 600rpx;
				 border-radius: 25px;
			 }
			 .login{
			    color:#49ad88;
				font-weight: 700;
			 }
			 .register{
				 margin-top: 40rpx;
				 color: #fff;
				 background-color: transparent;
				 border:1px solid #fff;
				 font-weight: 700;

			 }
		 }
	}
</style>
