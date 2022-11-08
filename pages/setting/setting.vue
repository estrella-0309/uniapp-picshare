<template>
	<view class="box">
		<view>	
		<div class="mask" ></div>
			<div class="list" >
				<view class="list-cell"  @click="ToMyself()">
					个人主页
					<u-icon name="arrow-right"></u-icon>
				</view>
				<view class="list-cell" @click="ToAlter()">
					修改资料
					<u-icon name="arrow-right"></u-icon>
				</view>
			</div>
			<div class="list">
				<view class="list-cell" @click="TOlogin()">
					切换账号
					<u-icon name="arrow-right"></u-icon>
				</view>
			</div>
			<div class="list">
				<view class="list-cell"  @click="open">
					版本号
					<u-icon name="arrow-right"></u-icon>
				</view>
				<view class="list-cell" @click="update()">
					更新版本
					<u-icon name="arrow-right"></u-icon>
				</view>
			</div>
			<u-button type="error" class="out"  @click="Logout()">退出登录</u-button>
			<uni-popup ref="popup" type="bottom"  background-color="#fff">
				<view  class="edition">
					版本号：1.0.0
				</view>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
			
			}
		},
		methods: {
			open(){
			    this.$refs.popup.open('bottom')
			},
			ToMyself(){
				uni.switchTab({
					url:"/pages/myself/myself"
				})
			},
			ToAlter(){
				uni.navigateTo({
					url:"/pages/alter/alter"
				})
			},
			TOlogin(){
				uni.navigateTo({
					url:"/pages/login/login"
				})
			},
			Logout(){
				uni.showToast({
					title:'退出成功',
					icon:'success',
					duration: 1000
				})	
				setTimeout(function(){
					uni.reLaunch({
						url:"/pages/login/login"
					})
				},1000)
				
			},
			update(){
				uni.showLoading({
					title:'加载中'
				})
				setTimeout(()=>{
					uni.hideLoading()
				},1000)
				setTimeout(()=>{
					uni.showToast({
						title:'已是最新版本了',
						icon:'success'
					})
				},1500)
			}
		},
		 
	}
</script>

<style lang="scss" scoped>
	/deep/ .u-index-list__letter{
		display: none;
	}
	.box{
		background-color: #f0f0f0;
		.mask{
			width: 750rpx;
			height: 100rpx;
		}
		.list{
			width: 90%;
			margin: 0 auto;
			margin-bottom: 30rpx;
			border-radius: 10px;
			background-color: #fff;
		
			.list-cell {
					display: flex;
					justify-content: space-between;
					box-sizing: border-box;
					width: 100%;
					padding: 10px;
					color: #323233;
					font-size: 16px;
					line-height: 40px;
					border-bottom: 1px solid #ccc;
					&:last-child {
						border:0;
					}
				}
			
		}
		.out{
			margin-top: 80%;
			width: 80%;
		}
		.edition{
			text-align: center;
			height: 200rpx;
			font-size: 20px;
			line-height: 200rpx;
		}
			
	}
</style>
