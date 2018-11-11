system = require('system')
address = system.args[1];
var page = require('webpage').create();
var url = address;

page.settings.resourceTimeout = 1000*10; // 10 seconds
page.onResourceTimeout = function(e) {
    console.log(page.content);
    console.log('bbbbb');
    phantom.exit(1);
};

page.settings.referer = 'https://detail.tmall.com/item.htm?id=44163831176';

page.open("https://detail.tmall.com/item.htm?id=44163831176", function (status) {
    //Page is loaded!
    if (status !== 'success') {
        console.log('aaaaa');
        phantom.exit();
    } else {
        page.addCookie({
            "t":"b20375fdd6a9d7bae6d68ab2e82334ce",
            "miid":"1041873275663106432",
            "hng":"N%7Czh-CN%7CCNY%7C156",
            "cna":"VwF6E06M9gICAZkiHyGOQwbz",
            "thw":"cn",
            "_cc_":"W5iHLLyFfA%3D%3D",
            "tg":"0",
            "mt":"ci=3_1&np=",
            "tracknick":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            "lgc":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            "tk_trace":"oTRxOWSBNwn9dPyorMJE%2FoPdY8zfvmw%2Fq5hldpnXy3QUpevB3p8wzyQhDOu1iYWUAQ2O\"CEfi7HUAiLenOK3c8ap3Rzop%2FngrnnKJ2a5ByEhXRQR27KBnEaEGM8tqr1x2iqFTBCoju6jGX0Pvf6J\"qvq0dGdZMJ%2F3dNBuZnZmWzk6sDgXL7D0y396wVS7eS8iKIe3BrpgvmfYDxPjY8rqRi%2FQt8Gd509Ps\"GNgW7mHbRBpHxIYTDgANpm8jVHup%2B151aFKLcUjntmq6fp4vpoU8SthvHGg%3D%3D",
            "_tb_token_":"e1e8fe138559",
            "v":"0",
            "uc1":"cookie16=VT5L2FSpNgq6fDudInPRgavC%2BQ%3D%3D\"cookie21=W5iHLLyFfXVRDP8mxoRA8A%3D%3D&cookie15=U%2BGCWk%2F75gdr5Q%3D%3D\"existShop=false&pas=0&cookie14=UoTYN4kNHIiXuw%3D%3D&tag=8&lng=zh_CN",
            "sg":"175",
            "_l_g_":"Ug%3D%3D",
            "csg":"56693162",
            "existShop":"MTU0MTgyMTY5Mw%3D%3D",
            "dnk":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            "_nk_":"5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            "isg":"BHZ2nH2nMSdpO8XEsPwRrt4VxKq4P-ptPAS6D-BfUNn0Ixe9SCWw4RyRPz_qkLLp"
        });

        // page.open(url, function (status) {
        //     //Page is loaded!
        //     if (status !== 'success') {
        //         console.log('Unable to post!');
        //     } else {
                console.log(page.content);
            // }
            phantom.exit();
        // });
    }
});

 