\<template>
	<view class="myself">
		<view class="nav">
			<view class="title">
				我的
			</view>
			<view class="nav-right" @click="Tosetting()">
				<u-icon name="setting"  size="22"></u-icon>
			</view>
		</view>
		<view>
			<view class="header">
				<view class="information">
					<view class="basis">
							<view class="avator">
								<u-avatar src="static/default.jpg" size="75" v-if="avatar==null"></u-avatar>
								  <u-avatar :src="avatar" size="75" v-else></u-avatar>
							</view>
							<view class="basismsg">
								<view class="username">
									{{username}}
								</view>
								<view class="">
									<view class="sex">
										<view class="text">{{sexx}}</view>
									</view>
									<view class="create">
										<view class="text">
											{{getDate(lastUpdateTime)}}
										</view>
									</view>
								</view>
							</view>
							
					</view>
					<view class="introduce">
						<view class="" v-if="introduce!==null">
							{{introduce}}
						</view>
						<view class="" v-else>
							这个人很懒，什么都没有留下
						</view>
					</view>
					<view class="mylists">
						<view class="focus">
							<view class="num">
								{{focusNum}}
							</view>
							<view>关注</view>
						</view>
						<view class="like">
							<view class="num">{{likeNum}}</view>
							<view >喜欢</view>
						</view>
						<view class="collections">
							<view class="num">{{collectNum}}</view>
							<view>收藏</view>
						</view>
						<view class="setting"> 	
							<view class="text" @click="Toalter" style="height: 100%;">编辑资料</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<div class="footer">
			<scroll-view class="scroll1" scroll-x="true">
				<view 
					:class="currentIndex==index?'select':'select1'" 
					:data-current="index" 
					@click="swichNav"
					v-for="(item,index) in scoll" 
					:key='index'>
						{{item.txt}}
				</view>
			</scroll-view>
			<swiper :autoplay="false"
				duration="100"
				@change="changeSwiper"
				:current="currentIndex"
				:style="{ height: swiperHeight+'px'}">
				<swiper-item v-for="(item, index) in dataList" :key="index">
					<view :id="'content-wrap' + index">
					  <item :type="item" ref="Data"></item>
					</view>
				</swiper-item>
			</swiper>
		</div>
	</view>
</template>

<script>
	import {mapState} from "vuex"
	import {GetCollect,Getlike,GetFocus} from "@/api/index/index.js"
	import item from "@/components/item/item.vue"
	import Checked from '@/mixins/index.js'
	export default {
		mixins: [Checked],
		components:{
			item
		},
		data() {
			return {
				swiperHeight: 0,
				focusNum:0,
				likeNum:0,
				collectNum:0,
				sexx:'男',
				currentIndex: 0,
				scoll: [{
					txt: '我的'
				},  {
					txt: '点赞'
				},
				{
					txt: '收藏'
				},
				],
				dataList: [{
					txt: 'My'
				},  {
					txt: 'Like'
				},
				{
					txt: 'Collect'
				},],
			}
		}, 
		onReachBottom(){
			this.$nextTick(async () =>{
				await this.$refs.Data[this.currentIndex].getData()
					this.setSwiperHeight()
				}
			)
		},
		onPullDownRefresh() {
			this.getdefaultData();
			uni.$emit("UpdateData");
			this.setSwiperHeight()
				setTimeout(function () {
					uni.stopPullDownRefresh();
				}, 1000);
		},
		async onLoad(){
			if(this.sex!==null){
				this.sexx=this.sex==1?'男':'女';
			}
			await this.getdefaultData();
			this.$nextTick(() => {
			      this.setSwiperHeight();
			    });
		},
		methods: {
			Tosetting(){
				uni.navigateTo({
						url:'/pages/setting/setting',
					});
			},
			async getdefaultData(){
				let result;
				result=await GetCollect(1,this.id);
				this.collectNum=result.data.total
				result=await Getlike(1,this.id);
				this.likeNum=result.data.total
				result=await GetFocus(1,this.id);
				this.focusNum=result.data.total 
			},
			getDate(date){
			      var time = new Date(Number(date));
			      var year= time.getFullYear() 
			      var month = ("0" + (time.getMonth() + 1)).slice(-2); 
			      var day = ("0" + time.getDate()).slice(-2);
			      var mydate = year + "-" + month + "-" + day;
			      return mydate
			    },
			changeSwiper(e) {
			      this.currentIndex = e.detail.current;
			      this.$nextTick(() => {
			        this.setSwiperHeight();
			      });
			},
			setSwiperHeight() {
				let element = "#content-wrap" + this.currentIndex;
				let query = uni.createSelectorQuery().in(this);
				query.select(element).boundingClientRect();
				query.exec((res) => {
					if (res && res[0]) {
					  this.swiperHeight = res[0].height;
					}
				  });
			},
			Toalter(){
				uni.navigateTo({
					url:'/pages/alter/alter'
				})
			},
			bindChange(e) {
							this.currentIndex = e.detail.current
						},
						swichNav: function(e) {
							var that = this;
							if (this.currentIndex === e.target.dataset.current) {
								return false;
							} else {
								this.currentIndex = e.target.dataset.current
							}
						}
		},
		computed: {
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
		},
	}
</script>

<style lang="scss" scoped>
	/deep/ .uni-nav-bar-text span{
			font-size: 16px ;
	}
	/deep/ .uni-scroll-view-content {
		display: flex;
		view{
			flex:1;
			text-align: center;
			font-size: 20rpx;
		}
	}
	.nav{
		padding-top: 70rpx;
		display: fixed;
		background-color: #f8f8f8;
		height: 44px;
		.title{
			float: left;
			font-size: 16px;
			margin-left: 340rpx;
			line-height: 44px;
		}
		width: 750rpx;
		.nav-right{
			float: right;
			margin-right: 40rpx;
			margin-top: 14rpx;
		}
	}
	.scroll1 {
		width: 100%;
		display:flex;
		background-color: #ffffff;
		& view {
			flex:1;
			font-size:25px;
		}
		& view:first-child:before {
			display: none;
		}
 
		.select {
			color: #f6a54d;
			border-bottom: 1px solid #f6a54d;
		}
		.select1 {
			color: #808080;
		}
	}	
	.text{
		width: 80%;
		border-radius: 40px;
		display: flex;
		justify-content: center;
		align-items: center;
		color: #fff;
		background-color:rgba(255, 255, 255, 0.5);
	}
	.myself{
		box-sizing: border-box;
		.header{
			background-color: #555360;
			width: 750rpx;
			height: 550rpx;
			display: flex;			
			.information{
				width: 750rpx;
				display: flex;
				flex-direction: column;
				justify-content: flex-end;
				margin-left: 40rpx;
				gap: 30px;
				.basis{
					width: 750rpx;
					display: flex;
					.avator{
						width: 150rpx;
						height: 150rpx;
						img{
							width: inherit;
							border-radius:50% ;
						}
					}
					.basismsg{
						margin-left: 20rpx;
						flex:2;
						display: flex;
						flex-direction: column;
						justify-content: space-between;
						text-align: center;
						view {
							display: flex;
						}
						.username{
							padding-left:20rpx;
							color: #fff;
							font-weight: 700;
							font-size: 25px;
							
						}
						.sex{
							width: 100rpx;
							color: #fff;
						}
						.create{
							width: 150px;
						}
					}
					
				}				
				.introduce{
					margin-left: 10rpx;
					color: #fff;
				}
				.mylists{
					display:flex;
					margin-bottom: 30px;
					margin-left: -40rpx;
					view{
						color: #fff;
						text-align: center;
						flex:1.1;
					}
					view:nth-child(3){
						flex: 1.5;
					}
					.setting{
						flex:2;
					}
				}
			}
		}
		.footer{
			width: 750rpx;
		}
	}
</style>
