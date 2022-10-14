<template>
	<view class="box">
		<view class="msg">
			<view class="title">
				标题：
				<el-input
				  placeholder="请输入内容"
				  v-model="title"
				  clearable 
				  style="width: 80%;">
				</el-input>
			</view>
			<view class="content">
				内容：
				<el-input
				  placeholder="请输入内容"
				  v-model="content"
				  clearable
				   style="width: 80%;">
				</el-input>
			</view>
		</view>
		<view class="images">
			<view class="images-title">
				上传照片（不能超过9张）
			</view>
			<el-upload
			  class="upload-demo"
			  ref="upload"
			  action="http://47.107.52.7:88/member/photo/image/upload"
			  :on-preview="handlePreview"
			  :on-remove="handleRemove"
			  :file-list="fileList"
			  :headers="headerObj"
			  :http-request="uploadFile"
			  :auto-upload="false"
			  multiple
			  >
			  <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
			  <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
			  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
			</el-upload>
		</view>
		
	</view>
</template>

<script>
	import{Upload} from '@/api/index/index.js'
	export default {
		data() {
			return {
				disabled: false,
				title:'',
				content:'',
				//  fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
				// headerObj:{
				// 	"Accept": "application/json, text/plain, */*",
				// 	"Content-Type": "multipart/form-data",
				// 	"appId": "24d8ed2ab0444b048cbd5fcdde289109",
				// 	"appSecret": "300002f6abcaf485d4cb19de0695a0b049dc0",
				// } ,
				fileData: '', // 文件上传数据（多文件合一）
						fileList: [],  // upload多文件数组
						uploadData: {
				     fieldData: {
				      id: '', // 机构id,
				     }
				    },
			}
		},
		methods: {
			 submitUpload() {
			        this.$refs.upload.submit();
			      },
			      handleRemove(file, fileList) {
			        console.log(file, fileList);
			      },
			      handlePreview(file) {
			        console.log(file);
			      },
				  uploadFile(file) {
				  		  this.fileData.append('files', file.file); // append增加数据
				  	},
			// async upLoadFile(data){
			// 	console.log(data,"in")
			// 	const file=data.file;
			// 	const form=new FormData();
			// 	console.log(file,"file");
			// 	form.append("file",file);
			// 	let result=await Upload(form);
			// 	console.log(result)
				
			// }
		}
	}
</script>

<style>
	
</style>
<style lang="scss" scoped>
/deep/ .el-upload--picture-card{
	width: 200rpx;
	height: 200rpx;
	i{
		position: relative;
		top: -40rpx;
	}
}
/deep/ .el-upload-list__item  {
	  width: 200rpx;
	  height: 200rpx;
	  margin-right: 31rpx;
	}
	.box{
		.summit{
			width: 750rpx;
			position: fixed;
			bottom: 100rpx;
			left: 0;
		}
		.msg {
			margin:50rpx 20rpx;
			.title{
				margin-bottom: 50rpx;
			}
		}
		.images{
			.images-title{
				margin-bottom: 20rpx;
				color: #ccc;
			}
			img{
				
				width: 200rpx;
				height: 200rpx;
			}
			margin: 50rpx 20rpx;
		}
	}
</style>
