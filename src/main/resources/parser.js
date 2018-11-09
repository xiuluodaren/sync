system = require('system')
address = system.args[1];
var page = require('webpage').create();
var url = address;

page.settings.resourceTimeout = 1000*10; // 10 seconds
page.onResourceTimeout = function(e) {
    console.log(page.content);
    phantom.exit(1);
};

page.open(url, function (status) {
    //Page is loaded!
    if (status !== 'success') {
    } else {
        phantom.addCookie({
            "cna":"qvQoFEX7FU0CATrSY2azUCbj",
            "hng":"CN%7Czh-CN%7CCNY%7C156",
            "_m_h5_tk":"fc0dfb0fbe0585677450a4562464ee2d_1541581393789",
            "_m_h5_tk_enc":"44e1a9c3284ab361b7814eaae8b83061",
            "lid":"%E5%8D%83%E6%89%8B%E4%BF%AE%E7%BD%97_11",
            "t":"48992099ff496509bc06a684eca439af",
            "tracknick":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            "lgc":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            "_tb_token_":"ed311f9318737",
            "swfstore":"219312",
            "dnk":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            "uc1":"cookie16=VFC%2FuZ9az08KUQ56dCrZDlbNdA%3D%3D&cookie21=W5iHLLyFfXVRDP8mxoRA8A%3D%3D&cookie15=U%2BGCWk%2F75gdr5Q%3D%3D&existShop=false&pas=0&cookie14=UoTYN4Y3CpHXJA%3D%3D&tag=8&lng=zh_CN",
            "_l_g_":"Ug%3D%3D",
            "unb":"823146037",
            "login":"true",
            "_nk_":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            "sg":"175",
            "csg":"69eaf68a",
            "cq":"ccp%3D0",
            "pnm_cku822":"098%23E1hveQvUvbpvjQCkvvvvvjiPR2zwAjlWRFSh0jEUPmPhAj1PRF5vljl8P2S9zjT5vpvhvvCCB2yCvvpvvhCv2QhvCvvvvvvEvpCWvv5xlBz9HFKzrmphQRA1%2BbeAOHbvT2eARdIAcUmxdX9Od56Ofwo4d34AVAdhaXTAdX3QbfmxdX9Od5OfwoOd34AVAnlYb8rj8td2dyCvm9vvhCvvvvvvvvvBGwvvvhtvvCj1QvvvD9vvhNjvvvmBvvvBYwvvvEWkphvCIhvpyPOs8yCvv9vvhh50A9QQ9hCvvOvChCvvvv%3D",
            "otherx":"e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0",
            "whl":"-1%260%260%260",
            "isg":"BJeXs2ncgArqAAS4XDCZFlIfJQshdDuCWs5Tt-nEsGbNGLda8ajojNY6fnij60O2",
            "x":"__ll%3D-1%26_ato%3D0"
        });

        page.open(url, function (status) {
            //Page is loaded!
            if (status !== 'success') {
                console.log('Unable to post!');
            } else {
                console.log(page.content);
            }
            phantom.exit();
        });
    }
});

 