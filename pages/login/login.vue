<template>
	<view class="box">
		<view class="text">
			SIGN IN
		</view>
		<view class="user">
			<input type="text" focus placeholder="用户名" v-model="username" adjustResize placeholder-style="color:#76c2a5"/>
			<input :type="type" placeholder="输入密码" v-model="password"  class="active"/>
			<u-icon :name="Icon" size="20" class="eyes" @click="flag=!flag"></u-icon>
			<view class="user-forget">
				<u>忘记密码?</u>
			</view>
		</view>
		<view class="btn">
			<button class="login" @click="login">登录</button>
			<button class="register" @click="toresgister">注册</button>
		</view>
	</view>
</template>

<script>
	import{Login} from '@/api/index/index.js'
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
			toresgister(){
				uni.navigateTo({
					url:"/pages/resgister/resgister"
				})
			},
			async login(){
				let result=await Login(this.username,this.password)
				if(result.code==200){
					this.$store.dispatch("getUserList",result.data);
					
					uni.showToast({
						title:'登录成功',
						icon:'success',
						duration: 1000
					})	
					setTimeout(function(){
						uni.switchTab({
							url:"/pages/index/index"
						})
					},1000)
				}
				
			},
		},
		computed:{
			type(){
				return this.flag?"text":"password";
			},
			Icon(){
				return !this.flag?"eye":"eye-off"
			}
		},
	
	}
</script>

<style lang="scss" scoped>
	
	::-webkit-input-placeholder{
		color:#76c2a5;
	}
	.active{
		box-sizing: border-box;
		padding: 5px;
		color: #fff;
		width: 600rpx;
		height: 100rpx;
		color: #000;
		background-color:rgba(255, 255, 255, 0.5);
		margin: 30rpx 80rpx;
		border-radius: 20px;
		padding-left: 60rpx;
		border: 0;
		outline: none;
	}
	.eyes{
		position: absolute;
		top:520rpx;
		right: 130rpx;
	}
	.box{
		background: url('../../static/background.png') no-repeat fixed center;
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
				margin: 30rpx auto;
				border-radius: 20px;
				padding-left: 60rpx;
				color: #000;
				background-color:rgba(255, 255, 255, 0.5);
			}
			.user-forget{
				float: right;
				padding-right:65rpx;
			}
		}
		.btn{
			 margin-top:400rpx;
			 button{
				 width: 600rpx;
				 border-radius: 25px;
			 }
			 .login{
			    color:#f6a84e;
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
