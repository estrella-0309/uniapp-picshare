<template>
	<view class="share">
		<!-- 内容输入框 -->
		<view class="msg">
			<view class="title">
				标题：
				<u-input
				  placeholder="请输入内容"
				  v-model="title"
				  class="title-input"
				>
				
				</u-input>
			</view>
			<view class="content">
				内容：
				<u-input
				  placeholder="请输入内容"
				  v-model="content"
				  class="title-input"
				  >
				</u-input>
			</view>
		</view>
		
		<!-- 内容发布 -->
		<view class="pics">
			<view class="medias" v-for="( image, index ) in uploadPicsList" :key="index">
			  <image
				class="img"
				:src="image.path"
				:data-src="image"
				@tap="previewImage(index)"
				mode="aspectFill"
			  />
			  <u-icon name="close" class="iclose" color="#eee" size="20" @tap="removeImage(index)" />
			</view>

			<!-- 选择照片按钮 -->
			<view class="uploadBtn" @tap="chosePicsAndUpload" v-if="uploadPicsList.length < 9">
			  <u-icon name="plus" size="60" color="#aaa" />
			  <view class="text">选择照片</view>
			</view>
		</view>

		<view class="btns">
			<view class="btnbtn">
				 <u-button type="primary" size="default"  :disabled="uploadStatus" class="save" @click="saveissue" >保存</u-button>
				 <u-button type="success" size="default" @click="sendFeed" :disabled="uploadStatus" class="send">发布动态</u-button>
				  <u-button type="primary" size="default" @click="open" :disabled="uploadStatus" class="savebox">保存箱</u-button>
			</view>
		
		</view>
		<uni-popup ref="popup" type="bottom" background-color="#fff">
					 <view class="popbox">
						 <u-empty text="暂无保存" mode="list" v-if="Save==null" style="height: 40%;"></u-empty>
					 	<view v-for="item in Save" :key="item.id" v-else>
					 		<view >
					 			<image :src="item.imageUrlList[0]" mode="" mode="scaleToFill" class="images"></image>
					 		</view>
							<div class="box-right">
								<view class="title">
									{{item.title}}
								</view>
								<view class="content">
									{{item.content}}
								</view>
							</div>
							<view class="box-issue">
								<u-button   size="mini" shape="square" type="primary" style="margin-top: 65rpx; width: 60%;" @click="saveissues(item)">发布</u-button>
							</view>
					 	</view>
					 </view>
		</uni-popup>

  </view>
</template>
<script>
	import {mapState} from "vuex"
	import {Upload,AddShare,Saveimg,GetSave,Change} from '@/api/index/index.js'
	export default {
		data() {
			return {
			  title:'test',
			  content:'test',
			  uploadPicsList: [],
			  uploadStatus: false,
			  imgCode:'',
			  show:true,
			  Save:[],
			  page:1,
			}
		},
		onLoad(){
			this.getsave();
		},
		methods: {
			async saveissues(item){
				let result=await Change(item.content,item.id,item.imageCode,item.pUserId,item.title) 
				if(result.code==200){
					uni.showToast({
						title:'发布成功',
						icon:'success',
						duration:1000,
					})
					this.getsave()
				}
			},
			open(){
				this.$refs.popup.open('buttom')
			},
			async getsave(){
				let result=await GetSave(this.page,this.id);
				console.log(result)
				if(result.code==200){
					if(result.data==null){
						this.Save=null
					}
					else{
						this.Save=result.data.records
					}
				}
				
			},
			chosePicsAndUpload() {
			  let count = 9 - this.uploadPicsList.length;
			  if (this.uploadPicsList.length >= 9) {
			    uni.showToast({
			      title: "最多选择9张照片",
			      icon: "success",
			      duration: 1000,
			    });
			    count = 0;
			    return false;
			  }
			  uni.chooseImage({
			    count, //默认9
			    sizeType: ["compressed"], //可以指定是原图还是压缩图，默认二者都有
			    sourceType: ["album", "camera"], //从相册选择
			    success: (res) => {
			      this.uploadPicsList = [...this.uploadPicsList, ...res.tempFiles];
			      if (this.uploadPicsList.length > 9) {
			        this.uploadPicsList = this.uploadPicsList.slice(0, 9);
			      }
			    },
			  });
			},
			// 预览图片
			previewImage(index) {
			  var current = this.uploadPicsList[index].path;
			  let urls = [];
			  this.uploadPicsList.map((item) => {
			    urls.push(item.path);
			  });
			  uni.previewImage({
			    current: current,
			    urls,
			  });
			},
			// 删除图片
			removeImage(index) {
				
			  this.uploadPicsList.splice(index, 1);
			},
			// 发布动态
			async saveissue(){
				this.uploadStatus = true
				if (this.title == "") {
				  uni.showToast({
				    title: "标题不能为空",
				    icon: "error",
				    duration: 1000,
				  })
				  this.uploadStatus = false
				  return
				}
				if (this.content == "") {
					
				  uni.showToast({
				    title: "描述不能为空",
				    icon: "error",
				    duration: 2000,
				  })
				
				  this.uploadStatus = false
				  return
				}
				
				if (this.uploadPicsList.length <= 0) {
				  uni.showToast({
				    title: "请选择照片",
				    icon: "error",
				    duration: 1000,
				  })
				  this.uploadStatus = false
				  return
				}
				await this.uploaded()
				let saveresult=await Saveimg(this.content,this.imgCode,this.id,this.title);
				this.uploadStatus = false
				 this.getsave()
				 
			},
			async uploaded(){
				const upimgslist=this.uploadPicsList.map(item=>{
								  return {
									  name:'fileList',
									  uri:item.path
								  }
				})
				let result=JSON.parse(await Upload(upimgslist));
				console.log(result); 
				if(result.code==500){
								 uni.showToast({
										title: '图片格式不支持',
										icon: 'error',
								 	})
									this.uploadStatus = false
									return
				}
				this.imgCode=result.data.imageCode;
			},
			async sendFeed() {
				this.uploadStatus = true
				if (this.title == "") {
				  uni.showToast({
				    title: "标题不能为空",
				    icon: "error",
				    duration: 1000,
				  })
				  this.uploadStatus = false
				  return
				}
				if (this.content == "") {
					
				  uni.showToast({
				    title: "描述不能为空",
				    icon: "error",
				    duration: 2000,
				  })
				
				  this.uploadStatus = false
				  return
				}
				
				if (this.uploadPicsList.length <= 0) {
					console.log(1)
				  uni.showToast({
				    title: "请选择照片",
				    icon: "error",
				    duration: 1000,
				  })
				  this.uploadStatus = false
				  return
				}
			  await this.uploaded();
			  let addresult=await AddShare(this.content,this.imgCode,this.id,this.title);
			  console.log(addresult);
			  if(addresult.code==200){
				   uni.hideToast();
				   uni.showToast({
				       title: "动态发布成功",
				       icon: "success",
				       duration: 1000,
					});
					this.uploadStatus=false;
					this.title='',
					this.content='',
					this.uploadPicsList=[];
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
<style lang="scss" scoped>
	.share {
		width: 750rpx;
		margin:100rpx 30rpx;
		.title-input{
			// background-color: red;
			position: relative;
			top: -55rpx;
			left: 80rpx;
			width: 73%;
		}
		.content{
			margin-top: 30rpx;
		}
	}

	.pics {
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: flex-start;
		align-items: center;
		align-content: center;
		padding-top: 20rpx;
		padding-bottom: 20rpx;
		// padding-left: 10rpx;

		.medias {
			width: 210rpx;
			height: 210rpx;
			background-color: #eee;
			border-radius: 6rpx;
			margin: 10rpx;
			position: relative;

			.iclose {
				position: absolute;
				right: 0;
				top: 0;
				background-color: rgba($color: #333, $alpha: 0.6);
				border-bottom-left-radius: 100%;
				padding: 10rpx 10rpx 16rpx 16rpx;
				z-index: 999;
			}
		}

		.img {
			width: 210rpx;
			height: 210rpx;
		
			border-radius: 6rpx;
		}

		.video {
			width: 210rpx;
			height: 210rpx;
			border-radius: 6rpx;
		}

		.uploadBtn {
			width: 210rpx;
			height: 210rpx;
			background-color: #eee;
			border-radius: 6rpx;
			margin: 5rpx;
			display: flex;
			flex-direction: column;
			flex-wrap: wrap;
			justify-content: center;
			align-items: center;
			align-content: center;

			.text {
				color: #888;
				font-size: 8rpx;
				margin-top: 20rpx;
			}
		}
	}
	.btns {
		width: 700rpx;
		position: fixed;
		bottom: 70rpx;
		left: 25rpx;
		.btnbtn{
			display: flex;
			.save{
				flex:1;
			}
			.send{
				margin: 0 20rpx;
				flex:3;
			}
			.savebox{
				flex:1;
			}
		}
	}
	.popbox>view{
		width: 750rpx;
		height: 200rpx;
		background-color: #f0f0f0;
		margin: 10rpx 0rpx;
		display: flex;
		.images{
			margin-left: 40rpx;
			width: 200rpx;
			height: 200rpx;
			flex:1;
		}
		.box-right{
			flex:3;
			.title{
				font-size: 20px;
				font-weight: 700;
				margin:20rpx 0 0 40rpx;
			}
			.content{
				color: #b2b2b2;
				margin-left:40rpx;
				margin-top: 10rpx;
			}
		}
		.box-issue{
			flex:1;
			margin-right: 50rpx;
		}
	}
</style>
