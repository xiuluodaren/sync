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

page.open(url, function (status) {
    //Page is loaded!
    if (status !== 'success') {
        console.log('aaaaa');
        phantom.exit();
    } else {
        // phantom.clearCookies();
        page.settings = {
            "XSSAuditingEnabled":true
        };

//         page.customHeaders = {
// "Accept-Language": "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2",
//             "Accept-Encoding": "gzip, deflate, br",
//                 "Connection": "keep-alive",
//             "Upgrade-Insecure-Requests": "1",
//                 "Pragma": "no-cache",
//             "Cache-Control": "no-cache"
//         };

        page.addCookie({
            // "cna":"qvQoFEX7FU0CATrSY2azUCbj",
            // "hng":"CN%7Czh-CN%7CCNY%7C156",
            // "_m_h5_tk":"fc0dfb0fbe0585677450a4562464ee2d_1541581393789",
            // "_m_h5_tk_enc":"44e1a9c3284ab361b7814eaae8b83061",
            // "lid":"%E5%8D%83%E6%89%8B%E4%BF%AE%E7%BD%97_11",
            // "t":"48992099ff496509bc06a684eca439af",
            // "tracknick":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            // "lgc":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            // "_tb_token_":"ed311f9318737",
            // "swfstore":"219312",
            // "dnk":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            // "uc1":"cookie16=VFC%2FuZ9az08KUQ56dCrZDlbNdA%3D%3D&cookie21=W5iHLLyFfXVRDP8mxoRA8A%3D%3D&cookie15=U%2BGCWk%2F75gdr5Q%3D%3D&existShop=false&pas=0&cookie14=UoTYN4Y3CpHXJA%3D%3D&tag=8&lng=zh_CN",
            // "_l_g_":"Ug%3D%3D",
            // "unb":"823146037",
            // "login":"true",
            // "_nk_":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            // "sg":"175",
            // "csg":"69eaf68a",
            // "cq":"ccp%3D0",
            // "pnm_cku822":"098%23E1hveQvUvbpvjQCkvvvvvjiPR2zwAjlWRFSh0jEUPmPhAj1PRF5vljl8P2S9zjT5vpvhvvCCB2yCvvpvvhCv2QhvCvvvvvvEvpCWvv5xlBz9HFKzrmphQRA1%2BbeAOHbvT2eARdIAcUmxdX9Od56Ofwo4d34AVAdhaXTAdX3QbfmxdX9Od5OfwoOd34AVAnlYb8rj8td2dyCvm9vvhCvvvvvvvvvBGwvvvhtvvCj1QvvvD9vvhNjvvvmBvvvBYwvvvEWkphvCIhvpyPOs8yCvv9vvhh50A9QQ9hCvvOvChCvvvv%3D",
            // "otherx":"e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0",
            // "whl":"-1%260%260%260",
            // "isg":"BJeXs2ncgArqAAS4XDCZFlIfJQshdDuCWs5Tt-nEsGbNGLda8ajojNY6fnij60O2",
            // "x":"__ll%3D-1%26_ato%3D0"


            // "cna":"qvQoFEX7FU0CATrSY2azUCbj",
            // "hng":"CN%7Czh-CN%7CCNY%7C156",
            // "_m_h5_tk":"fc0dfb0fbe0585677450a4562464ee2d_1541581393789",
            // "_m_h5_tk_enc":"44e1a9c3284ab361b7814eaae8b83061",
            // "lid":"%E5%8D%83%E6%89%8B%E4%BF%AE%E7%BD%97_11",
            // "cq":"ccp%3D0",
            // "t":"48992099ff496509bc06a684eca439af",
            // "tracknick":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            // "lgc":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            // "_tb_token_":"ed311f9318737",
            // "swfstore":"219312",
            // "dnk":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            // "uc1":"cookie16=VFC%2FuZ9az08KUQ56dCrZDlbNdA%3D%3D&cookie21=W5iHLLyFfXVRDP8mxoRA8A%3D%3D&cookie15=U%2BGCWk%2F75gdr5Q%3D%3D&existShop=false&pas=0&cookie14=UoTYN4Y3CpHXJA%3D%3D&tag=8&lng=zh_CN",
            // "_l_g_":"Ug%3D%3D",
            // "unb":"823146037",
            // "login":"true",
            // "_nk_":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            // "sg":"175",
            // "csg":"69eaf68a",
            // "x":"__ll%3D-1%26_ato%3D0",
            // "pnm_cku822":"098%23E1hvepvUvbpvUvCkvvvvvjiPR2zwsj1nRLMW1jEUPmPOzjYEPFFyAj3bP2spsjtWRphvCvvvphvPvpvhvv2MMQhCvvOvChCvvvmtvpvIvvCvpvvvvvvvvhNjvvvCWpvvBGwvvvEOvvCj1QvvvypvvhNcvvvmL8yCvv9vvhh50GuCHOyCvv4CvhE20WQEvpCWvw68m3lljd8rVC6s%2BXZz%2BsI6N6qhtE%2BfjXrQpd2XrqpAhjCbFO7t%2B3vXJ9kx6fItn1vDN%2BLUdigBKoiE3c0ShB4AVAdpaXTAdX3AbTwCvvpvvhHh",
            // "whl":"-1%260%260%260",
            // "isg":"BFpa-pNu1bF9PFkjEYcM3QcQqAC8I47ZT3HuDGTTDu241_oRTBiwdxQlo2NuHFb9"


            "_l_g_":"Ug%3D%3D",
            "_m_h5_tk_enc":"44e1a9c3284ab361b7814eaae8b83061",
            "_m_h5_tk":"fc0dfb0fbe0585677450a4562464ee2d_1541581393789",
            "_nk_":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            "_tb_token_":"ed311f9318737",
            "cna":"qvQoFEX7FU0CATrSY2azUCbj",
            "cookie1":"URwV2Q5tPIIAuqI6Il8IkxqnuLBP33zywbYVRSOFbf4%3D",
            "cookie2":"19ac3ce5741912e46ede57ab0ca869eb",
            "cookie17":"W8t3v0gDyVm4",
            "cq":"ccp%3D0",
            "csg":"69eaf68a",
            "dnk":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            "hng":"CN%7Czh-CN%7CCNY%7C156",
            "isg":"BC4ufOnh-fw-qw0nDaPwIfP8fIQwh6KVG40aCFj3hzH7O86VwLy-O5g592dy-OpB",
            "lgc":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            "lid":"%E5%8D%83%E6%89%8B%E4%BF%AE%E7%BD%97_11",
            "login":"true",
            "otherx":"e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0",
            "pnm_cku822":"098%23E1hvepvUvbpvUvCkvvvvvjiPR2zwsj1nRLMW1jEUPmPOzjYEPFFyAj3bP2spsjtWRphvCvvvphvPvpvhvv2MMQhCvvOvChCvvvmtvpvIvvCvpvvvvvvvvhNjvvvCWpvvBGwvvvEOvvCj1QvvvypvvhNcvvvmL8yCvv9vvhh50GuCHOyCvv4CvhE20WQEvpCWvw68m3lljd8rVC6s%2BXZz%2BsI6N6qhtE%2BfjXrQpd2XrqpAhjCbFO7t%2B3vXJ9kx6fItn1vDN%2BLUdigBKoiE3c0ShB4AVAdpaXTAdX3AbTwCvvpvvhHh",
            "sg":"175",
            "swfstore":"219312",
            "tracknick":"%5Cu5343%5Cu624B%5Cu4FEE%5Cu7F57_11",
            "t":"48992099ff496509bc06a684eca439af",
            "uc1":"cookie16=VFC%2FuZ9az08KUQ56dCrZDlbNdA%3D%3D&cookie21=W5iHLLyFfXVRDP8mxoRA8A%3D%3D&cookie15=U%2BGCWk%2F75gdr5Q%3D%3D&existShop=false&pas=0&cookie14=UoTYN4Y3CpHXJA%3D%3D&tag=8&lng=zh_CN",
            "uc3":"vt3=F8dByR%2FJzXzyf7gL1r0%3D&id2=W8t3v0gDyVm4&nk2=pMZ01mlWLxu1Ywg%3D&lg2=U%2BGCWk%2F75gdr5Q%3D%3D",
            "unb":"823146037",
            "whl":"-1%260%260%260",
            "x":"__ll%3D-1%26_ato%3D0",
            "{79029345-2c34-427c-bc7b-9123eee34455}":"value"

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

 