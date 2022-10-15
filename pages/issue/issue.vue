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
		  <u-button type="primary" size="default" @click="sendFeed" :disabled="uploadStatus">发布动态</u-button>
		</view>

  </view>
</template>
<script>
	import {mapState} from "vuex"
	import {Upload,AddShare} from '@/api/index/index.js'
	export default {
		data() {
			return {
			  title:'test',
			  content:'test',
			  uploadPicsList: [],
			  uploadStatus: false,
			  imgCode:'',
			}
		},
		methods: {
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
			async sendFeed() {
			  // 如果正在上传中则等待
			  if (this.uploadStatus) return
			  this.uploadStatus = true
			  // 如果描述为空则不进行发布
			  if (this.title == "") {
			    uni.showToast({
			      title: "标题不能为空",
			      icon: "loading",
			      duration: 1000,
			    })
			    this.uploadStatus = false
			    return
			  }
			  if (this.content == "") {
			    uni.showToast({
			      title: "描述不能为空",
			      icon: "loading",
			      duration: 1000,
			    })
			    this.uploadStatus = false
			    return
			  }

			  if (this.uploadPicsList.length <= 0) {
			    uni.showToast({
			      title: "请选择照片",
			      icon: "loading",
			      duration: 1000,
			    })
			    this.uploadStatus = false
			    return
			  }
			  
			  // uni.showToast({
			  //   title: "动态发布中...",
			  //   icon: "loading",
			  //   duration: 60000,
			  // });
			  console.log(this.uploadPicsList)
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
	}
</style>
