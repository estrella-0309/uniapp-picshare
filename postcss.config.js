 module.exports = {
   plugins: {
     autoprefixer: {},
 	  'postcss-px-to-viewport': {
 	       unitToConvert: 'rpx', // 需要转换的单位
 	       viewportWidth: 750,
 	       unitPrecision: 5,
 	       propList: ['*'],
 	       fontViewportUnit: 'rem', // 字体需要转成的单位，只针对 font-size 属性
 	       selectorBlackList: [],
 	       minPixelValue: 1,
 	       mediaQuery: false,
 	       replace: true,
 	       exclude: undefined,
 	       include: undefined,
 	       landscape: false
 	     },
 	     '@dcloudio/vue-cli-plugin-uni/packages/postcss': {}
   }
 }
