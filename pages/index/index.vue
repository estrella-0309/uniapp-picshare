<template>
	<view class="app">
		<view v-if="!list.length>0">
			
			<u-empty text="还没有关注捏" mode="list" style="width:750rpx;height: 100vh;"></u-empty>
		</view>
				<view class="index-list" 
					v-for="item in list"
				  :key="item.id" @click="Todetail(item)" v-else>
				  <view class="header">
				  	<view class="header-left" >
						<view class="avator" >	
							<u-avatar :text="item.username.substr(0,1)" size="50"></u-avatar>
						</view>
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
								<u-icon  :label="item.likeNum" :name="item.hasLike?'thumb-up-fill':'thumb-up'"  size="25" class="iconimg-2" ></u-icon>	
							</view>
							<view class="good" @click.stop="ClickCollect(item,id)">
								<u-icon  :label="item.collectNum" :name="item.hasCollect?'star-fill':'star'"  size="25" class="iconimg-2" ></u-icon>	
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
				console.log(this.list)
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
							console.log(res.data.records,"focus")	
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
					console.log(res)
					if(res.code==200){
							console.log(res.data.recoSrds,"focus")
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
	float: left;
	background-color: #f0f0f0;
	.index-list{
		margin-bottom: 50rpx;
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
				padding: 30rpx 30rpx;
				position: absolute;
				.avator{
					// position: relative;
					// width: 100rpx;
					color: red;
					span {
						
					}
				
				}
				.username{
					font-size: 20px;
					font-weight: 700;
					position: relative;
					top:-100rpx;
					left:130rpx;
					display: inline-block;
				}
				.createtime{
					font-size: 14px;
					color: #ccc;
					position: relative;
					top:-50rpx;
					left:80rpx;
					display: inline-block;
				}
			}
			.header-right{
				margin-right: 50rpx;
				margin-top: 55rpx;
				
				float: right;
				
			}
		}
		.picture{
			float: left;
			width: 730rpx;
			margin: 0 35rpx;
			// display:flex;
			flex-wrap: wrap;
			image{
				// flex:1;
				margin-right: 20rpx;
				max-width: 210rpx;
				max-height: 210rpx;
				
			}
		}
		.footer{
			float: left;
			width: 750rpx;
			margin-top: 20rpx;
			padding-left: 35rpx;
			.footer-left{
				float: left;
			}
			.title{
				font-size: 20px;
				font-weight: 700;
			}
			.content{
				padding-top: 20rpx;
				width: 700rpx;
				color: #8c8c8c;
			}
			
			.goodandcollect{
				float: right;
				margin-right: 60rpx;
				.good{
					float: left;
					margin-left: 30rpx;
					.goodnums{
						float: left;
						margin-top: 26rpx;
						margin-right: 10rpx;
					}
					.collectnums{
						float: left;
						margin-top: 26rpx;
						margin-right: 10rpx;
					}
				}
				
			}
		}
	}
	
	
}
</style>

