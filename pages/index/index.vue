<template>
	<view class="app">
		<view v-if="!list.length>0">
			<u-empty text="还没有关注捏" mode="list" style="width:750rpx;height: 100vh;"></u-empty>
		</view>
		<view class="index-list" 
			v-for="item in list"
			:key="item.id" 
			@click="Todetail(item)" v-else>
			<view class="header">
				<view class="header-left" >
					<view class="avator" >	
						<u-avatar :text="item.username.substr(0,1)" font-size="25" randomBgColor></u-avatar>
					</view>					
				</view>		  	
				<view class="header-right">
					<view class="username">{{item.username}}</view>
					<view class="createtime">
						{{formatDateTime(item.createTime)}}发布
					</view>	
				</view>	
			</view>			 
			<view class="picture" >
				<template v-for="imgs in item.imageUrlList">
					<image :src="imgs" alt=""  mode="aspectFill" @click.stop="previewImage(item.imageUrlList)"></image>
				</template> 
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
					<view class="good" @click.stop="ClickLike(item,id)">
						<u-icon  
							:label="item.likeNum" 
							:name="item.hasLike?'thumb-up-fill':'thumb-up'"   
							color="#f4ea2a" 
							size="25"
							class="iconimg-2" >
						</u-icon>	
					</view>
					<view class="good" @click.stop="ClickCollect(item,id)">
						<u-icon  
							:label="item.collectNum" 
							:name="item.hasCollect?'star-fill':'star'" 
							color="#f4ea2a" 
							size="25" 
							class="iconimg-2" >
						</u-icon>	
					</view>
				</view>	
			</view>
		</view>
	</view>
</template>

<script>
import {GetFocus} from "@/api/index/index.js"
import {mapState} from "vuex"
import Checked from '@/mixins/index.js'
export default {
		mixins: [Checked],
		data() {
			return {				
				page:1,					
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
			previewImage(imgs) {
			  uni.previewImage({ 
				urls:imgs,
			  });
			},
			Todetail(item){
				uni.navigateTo({
					url:'/pages/detail/detail?item='+JSON.stringify(item),
				});
			},
			async init(){
				let res=await GetFocus(1,this.id);
				if(res.code==200){
					for(let i=0;i<res.data.records.length;i++){
						res.data.records[i].hasFocus=true;
					}
					this.list=res.data.records;
					this.page++;
				} 
				else{
					uni.showToast({
						title:res.msg,
						icon:'error',
						duration:1000,
					})
				}
			},
			async getData(){
				let res=await GetFocus(this.page,this.id);
				if(res.code==200){
					for(let i=0;i<res.data.records.length;i++){
						res.data.records[i].hasFocus=true;
					}
					this.list=[...this.list,...res.data.records];
					this.page++;		
				} 
				else{
					uni.showToast({
						title:res.msg,
						icon:'error',
						duration:1000,
					})
				}
			}	
		},
		computed:{
			...mapState({
				id:state=>state.user.id,
			}),
		}
	}
</script>

<style scoped lang="scss">
	/deep/ .u-text{
		span{
			margin-top: -10rpx;
		}
		
	}
	.iconimg{
		margin-top: 50rpx;
		width: 80rpx;
		height:80rpx;
	}
	.iconimg-2{
		margin-top: 12rpx;
		width:60rpx;
		height:60rpx;
	}
	.app{
		background-color: #f0f0f0;
		.index-list{
			margin: 0 auto;
			margin-bottom: 50rpx;
			background-color: #fff;
			width: 700rpx;
			border-radius: 20px;
			box-shadow: 0 5rpx 5rpx rgba(10, 16, 20, 0.24), 0 0 16rpx rgba(10, 16, 20, 0.12);
			  	text-decoration: none;
			.header{
				box-sizing: border-box;
				height: 150rpx;
				display: flex;
				.header-left{
					padding: 30rpx 30rpx;	
				}
				.header-right{
					margin-top: 30rpx;
					.username{
						font-size: 20px;
						font-weight: 700;
					}
					.createtime{
						font-size: 14px;
						color: #ccc;
					}
				}
			}
			.picture{
				width: 730rpx;
				margin: 0 20rpx;
				flex-wrap: wrap;
				image{
					margin-right: 20rpx;
					max-width: 210rpx;
					max-height: 210rpx;
				}
			}
			.footer{
				width: inherit;
				margin-top: 20rpx;
				padding-left: 35rpx;
				display: flex;
				justify-content:space-between;
				.footer-left{
					
					.title{
						font-size: 20px;
						font-weight: 700;
					}
					.content{
						padding-top: 20rpx;
						color: #8c8c8c;
					}
				}			
				.goodandcollect{
					align-self: flex-end;
					display: flex;
					margin-right: 60rpx;
					.good{
						margin-left: 30rpx;
						.goodnums{
							margin-top: 26rpx;
							margin-right: 10rpx;
						}
						.collectnums{
							margin-top: 26rpx;
							margin-right: 10rpx;
						}
					}
				}
			}
		}
	}
</style>

