<template>
	<view class="app">
		<view class="index-list" >
			<view class="header">
				<view class="header-left" >
					<view class="avator" >	
						<u-avatar :text="data.username.substr(0,1)" size="50"></u-avatar></view>
				  			<view class="username">{{data.username}}</view>	
							<view class="createtime">
								{{formatDateTime(data.createTime)}}发布
							</view>					
				  	</view>	
							<view class="header-right">
								 <view class="focus">
									<u-icon  label="关注" :name="data.hasFocus?'heart-fill':'heart'" size="35" label-size="25" @click="ClickFocus(data,userid)"></u-icon>
								 </view>			 	
							</view>
				</view>
				<view class="footer">
					 <view class="footer-left">
						<view class="title">
							{{data.title}}
						</view>
						<view class="content">
							{{data.content}}
						</view>
					</view>
				</view>				 
				 <view class="picture" >
					<swiper class="swiper" circular indicator-dots="true" autoplay="false" interval="5000"duration="1000">
						<swiper-item v-for="(item,index) in data.imageUrlList" :key="index">
							<view class="swiper-item uni-bg-red">						
								<template>
									<u-image width="100%" height="500rpx" :src="item"  @click="previewImage(index)" :lazy-load="true"></u-image>
								</template>
							</view>
						</swiper-item>
					</swiper>
				  </view>	

			</view>
			<!-- <u-line color="blue"></u-line> -->
				<comment :commentList="commentList" :id="this.data.id"></comment>
				<view class="fixedbar" v-if="istrue"> 
					<view class="like" @click="ClickLike(data,userid)">
						<u-icon  label="点赞" :name="data.hasLike?'thumb-up-fill':'thumb-up'"  size="35"  label-size="25"></u-icon>
						
					</view>
					
					<view class="comment">
						<u-icon  label="评论" name="email" size="35"  label-size="25" @click="istrue=false"></u-icon>
					</view>
				
					<view class="collect"    @click="ClickCollect(data,userid)">
						<u-icon  label="收藏" :name="data.hasCollect?'star-fill':'star'" size="35"  label-size="25"></u-icon>
					</view>
				</view>
				<view class="fixedbar" v-else="istrue" >
					
					<u-input v-model="commentmsg" placeholder="请输入评论内容" type="text"  :adjust-position="true" :focus="true" :auto-height="true" class="sendmsg" @blur="Onblur()" />
					<u-button class="sendbtn" :plain="true" @click="sendmsg(btn?'?':btnmsg)"> 发送</u-button>
				</view>
		</view> 
	
</template>

<script>
import {Detail,AddFirstComment,FirstComment,GetSecondComment,SecondComment} from "@/api/index/index.js"
import {mapState} from "vuex"
import Unchecked from '@/static/checked.png'
import Ungood from '@/static/good.png'
import good from '@/static/good-h.png'
import checked from '@/static/checked-h.png'
import Uncollect from '@/static/collect.png'
import collect from '@/static/collect-h.png'
import Checked from '@/mixins/index.js'
import comment from '@/components/comment/index.vue'
export default {
	mixins: [Checked],
	components:{
		comment
	},
			data() {
				return {
					data:{},
					tempimg:[],
					commentmsg:'',
					commentList:[],
					istrue:true,
					page:1,
					btn:true,
					btnsmg:{},
				}
			},
			onLoad(option) {
				this.data=option.item;
				this.data=JSON.parse(this.data)
				this.initcomment();		
				let _this=this
				uni.$on("sendsecondmsg",function(data){
					_this.istrue=false
					_this.btn=false;
					_this.btnmsg=data.data;
				})
			},
			onReachBottom(){

			},
			methods: {
				async initcomment(){
					let result=await FirstComment(this.page,this.data.id)	
					if(result.code=200){
						this.commentList=result.data.records;
					}
					this.getSecond();
				},
				async getSecond(){
					for(let [index,item] of this.commentList.entries()){
						let result=await GetSecondComment(1,this.data.id,item.id); 
						if(result.data==null){
							this.$set(item,'secondcommentrecords',[])
						}
						else{
							this.$set(item,'secondcommentrecords',result.data.records)
						}
						
					}
				},
				previewImage(index) {
				  uni.previewImage({ 
				    current:index,
					urls:this.data.imageUrlList,
				  });
				},
				Onblur(){
					let _this=this;
					setTimeout(function(){
						_this.btn=true;
						_this.istrue=true
					},100)

				},
				async sendmsg(temp){
					this.istrue=false
					if(this.btn){
						let result=await AddFirstComment(this.commentmsg,this.data.id,this.userid,this.username);
						console.log(result,"first")
						if(result.code==200){
							uni.showToast({
											title:"评论成功",
											icon:'success',
											duration:1000,
							})
						}
					}
					else{

						let result=await SecondComment(this.commentmsg,temp.id,temp.pUserId,temp.id,temp.pUserId,this.userid,this.data.id,this.username)
						console.log(result,"second")
						this.btn=true;
					}
					this.initcomment(this.page)
				}
			},
			computed:{
				...mapState({
					userid:state=>state.user.id,
					username:state=>state.user.username,
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
	/deep/ .swiper-pagination-bullet-active {
    background-color: red;
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
	.fixedbar{
		width:750rpx;
		position: fixed;
		bottom:0;
		border-radius: 5rpx;
		background-color: #f8f9f9;
		display:flex;
		&>view{
			padding-left: 20rpx;
			border-right:1px solid #ddd;
			flex: 1;
		}
		.collect{
			border: 0;
		}
		.sendmsg{
			flex:8;
		}
		.sendbtn{
			flex:2;
		}
	}
	.index-list{
		margin-bottom: 100rpx;
		background-color: #fff;
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
					width: 100rpx;
					font-size: 30px;
					font-weight: 700;
					position: relative;
					top:-120rpx;
					left:130rpx;
					display: inline-block;
				}
				.createtime{
					font-size: 14px;
					color: #ccc;
					position: relative;
					top:-60rpx;
					left:30rpx;
					display: inline-block;
				}
			}
			.header-right{
				float: right;
				margin:30rpx 30rpx 0 0;
			}
		}
		.picture{	
			// margin: 100rpx 0;
			height: 450rpx;
			width: 750rpx;			
			.swiper{
			// 	width: 750rpx;
				height: 500rpx;
			// 	margin-top: 300rpx ;
				image{
					width: 750rpx;

				}
			}
		}
		.footer{
			width: 750rpx;
			margin-top: 20rpx;
			padding-left: 35rpx;
			.title{
				font-size: 30px;
				font-weight: 700;
			}
			.content{
				word-break:break-word;
				max-width: 690rpx;
				padding-top:10rpx;
				color: #8c8c8c;
				font-size: 20px;
				
			}
			margin-bottom: 40rpx;
		
		}
	}
	
	
}
</style>

