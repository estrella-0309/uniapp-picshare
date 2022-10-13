<template>
	<view class="app">
		<view v-for="item in list">
			<view class="index-list" 
			 v-for="(item2,index) in item.imageUrlList" 
			  :key="index">
			  <view class="header">
			  	<view class="header-left" >
			  			<img  src="static/default.jpg" alt="">		
			  			<view>{{item.username}}</view>						
			  	</view>
			  	<view class="header-right">
			  		<view>关注</view>
			  		<img src="static/checked.png" alt="" style="width: 40px;">
			  	</view>			  	
			  </view>
			  <view class="mid" >
			  	<img :src="item2" alt="">
			  </view>
			  <view class="footer">
				  <view class="footer-left">
				  	<view class="title">
				  		{{item.title}}
				  	</view>
				  	<view class="content">
				  		{{item.content}}
				  	</view>
				  </view>
				  
					<view class="goodandcollect">
						<view class="good">
							<view class="goodnums">
								666
							</view>
							<img src="static/good.png" alt="">
						</view>
						<view class="good">
							<view class="collectnums">
								666
							</view>
							<img src="static/collect.png" alt="">
						</view>
					</view>
					
			  </view>
			</view>
			  </view>
		</view>
	
</template>

<script>
	import {mapState} from "vuex"
import loginVue from "../login/login.vue";
	export default {
		data() {
			return {
				page:1,
				flag:true,
				list:[],
			}
		},
		onLoad(option) {					
			uni.startPullDownRefresh();
		},
		onPullDownRefresh() {
			this.init();
				setTimeout(function () {
					uni.stopPullDownRefresh();
				}, 1000);
		},
		onReachBottom(){
			this.getData();
		},
		methods: {
			init(){
				uni.request({
					url:'http://47.107.52.7:88/member/photo/focus',
					method:'GET',
					header:{
						  // "Accept": "application/json, text/plain, */*",
						  // "Content-Type": "application/x-www-form-urlencoded",
						  // "appId": "24d8ed2ab0444b048cbd5fcdde289109",
						  "appId": "d39fc189485c43d9a4b37463b238ac84",
						  // "appSecret": "300002f6abcaf485d4cb19de0695a0b049dc0",
						  "appSecret": "06219a004b5ecf6c84f89ba5f9d5c81a037f6"
					},
					data:{
						current:this.page,
						size:2,
						userId:this.id
					},
					success:res=>{
						
						 if(res.data.code==200){
		
							if(parseInt(res.data.data.current)*parseInt(res.data.data.size)>parseInt(res.data.data.total)){
								uni.showToast({
												title:'没有关注的内容了',
												icon:'none',
												duration:1000,
								})
								return;
							}
							else{
								this.list=res.data.data.records;
								this.page++;
							}
							console.log(this.list,"list");
							// this.list.push.apply(this.list,res.data.data.records);
							
						} 
						else{
							uni.showToast({
											title:res.data.msg,
											icon:'error',
											duration:1000,
							})
						}
					}
				})
			},
			getData(){
				uni.request({
					url:'http://47.107.52.7:88/member/photo/focus',
					method:'GET',
					header:{
						  "Accept": "application/json, text/plain, */*",
						  "Content-Type": "application/x-www-form-urlencoded",
						  // "appId": "24d8ed2ab0444b048cbd5fcdde289109",
						  "appId": "d39fc189485c43d9a4b37463b238ac84",
						  // "appSecret": "300002f6abcaf485d4cb19de0695a0b049dc0",
						  "appSecret": "06219a004b5ecf6c84f89ba5f9d5c81a037f6"
					},
					data:{
						current:this.page,
						size:2,
						userId:this.id
					},
					success:res=>{
						console.log(res)
						if(res.data.code==200){
							this.list.push.apply(this.list,res.data.data.records);
							this.page++;
						}
					}
				})
			}
		},
		computed:{
			underline(){
				return this.flag?"active":"";
			},
			underline2(){
				return !this.flag?"active":"";
			},
			...mapState({
				id:state=>state.user.id,
				username:state=>state.user.username,
				appKey:state=>state.user.appKey,
				password:state=>state.user.password,
				sex:state=>state.user.sex,
				introduce:state=>state.user.introduce,
				avatar:state=>state.user.avatar,
				createTime:state=>state.user.createTime,
				lastUpdateTime:state=>state.user.lastUpdateTime
			}),
		}
	}
</script>

<style scoped lang="scss">
	.app{
		float: left;
		background-color: #f0f0f0;
		.index-list{
			margin-bottom: 100rpx;
			background-color: #fff;
			float: left;
			width: 750rpx;
			.header{
				box-sizing: border-box;
				width: 750rpx;
				height: 150rpx;
				.header-left{
					float: left;
					width: 300rpx;
					padding: 15rpx;
					img{
						width: 120rpx;
						height: 120rpx;
						border-radius:50%;
					}
					view{
						font-size: 20px;
						font-weight: 700;
						position: relative;
						top:-40rpx;
						left:23rpx;
						display: inline-block;
						height: 150rpx;
						line-height: 220rpx;
					}
				}
				.header-right{
					margin-right: 50rpx;
					view{
						margin-top: 60rpx;
						margin-right: 10rpx;
						float: left
					}
					img{
						margin-top: 40rpx;
						float: right;
					}
					float: right;
					
				}
			}
			.mid{
				float: left;
				width: 730rpx;
				margin: 0 10rpx;
				// height: 460rpx;
				// overflow: hidden;
				img{
					max-height:560rpx;
					max-width: 700rpx;
					
					
				}
			}
			.footer{
				float: left;
				width: 750rpx;
				margin-top: 20rpx;
				padding-left: 20rpx;

				.footer-left{
					float: left;
				}
				.title{
					font-size: 20px;
					font-weight: 700;
				}
				.content{
					color: #8c8c8c;
				}
				.goodandcollect{
					float: right;
					margin-right: 80rpx;
					.good{
						float: left;
						margin-left: 30rpx;
						.goodnums{
							float: left;
							margin-top: 14rpx;
						}
						.collectnums{
							float: left;
							margin-top: 14rpx;
						}
						img{
							padding-left: 10rpx;
							width: 60rpx;
							height: 60rpx;
						}
					}
					
				}
			}
		}
		
		
	}
</style>
