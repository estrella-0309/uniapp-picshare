<template>
	<view>
		<view class="box">
			<view class="text">
				注册
			</view>
			<view class="user">
				<input 
					type="text" 
					focus 
					placeholder="用户名" 
					v-model="username" 
					adjustResize 
					/>
					<input
						:type="type" 
						placeholder="请输入密码" 
						v-model="password" 
						style="margin-top: 70rpx;" 
						class="active"/>		
				<u-icon :name="Icon" size="20" class="eyes" @click="flag=!flag"></u-icon>
				<input 
					:type="type" 
					placeholder="请再次输入密码" 
					v-model="password2" 
					style="margin-top: 70rpx;" 
					class="active"/>		
				<u-icon :name="Icon" size="20" class="eyes2" @click="flag=!flag"></u-icon>
			</view>
			<view class="btn">
				<button class="register" @click="resgister" style="background-color: #fff;color: #f6a84e;">注册</button>
				<button class="register" @click="tologin">返回登录</button>
			</view>
		</view>
	</view>
</template>

<script>
import{Register} from '@/api/index/index.js'
	export default {
		data() {
			return {
				flag:false,
				
				username:"",
				password:"",
				password2:"",
			}
		},
		methods: {
			tologin(){
				uni.navigateTo({
					url:"/pages/login/login"
				})
			},
			async resgister(){ 
				if(this.password!=this.password2){
					uni.showToast({
						title: "两次输入密码不相同！",
						icon:'error',
						duration:1000,
					});
					return;
				}
				let result=await Register(this.username,this.password);
				if(result.code==200){
					uni.showToast({
						title: "注册成功",
						icon:'success',
						duration:1000,
					});
					setTimeout(function(){
						uni.navigateTo({
							url:'/pages/login/login'
						})
					},1000)
				}
				else if(result.code==500){
					uni.showToast({
						title: result.msg,
						icon:'error',
						duration:1000,
					})
				}
			
			}
		},
		computed:{
			type(){
					return this.flag?"text":"password";
				},
				Icon(){
					return !this.flag?"eye":"eye-off"
				},

		},
		
	}
</script>

<style lang="scss" scoped>
	::-webkit-input-placeholder{
		color:grey;
	}
	.active{
		box-sizing: border-box;
		padding: 5px;
		// color: #fff;
		width: 600rpx;
		height: 100rpx;
		background-color:rgba(255, 255, 255, 0.5);
		margin: 30rpx 80rpx;
		border-radius: 20px;
		padding-left: 60rpx;
		border: 0;
		outline: none;
	}
	.eyes{
		position: absolute;
		top:555rpx;
		right: 130rpx;
		color: #76c2a5;
	}
	.eyes2{
		position: absolute;
		top:723rpx;
		right: 130rpx;
		color: #76c2a5;
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
				color: #76c2a5;
			}
		}
		.btn{
			 margin-top:80rpx;
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
