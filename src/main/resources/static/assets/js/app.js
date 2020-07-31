VANTA.NET({
    el: "#intro",
    mouseControls: true,
    touchControls: true,
    minHeight: 400,
    minWidth: 400,
    scale: 1.00,
    scaleMobile: 1.00
})


$("#clearAttackHistory").click(function () {
    fetch("http://localhost:8080/home/launch/clear")
    $("#attackHistoryTBody").empty();
})


$("#refreshAttackHistory").click(function () {
let attacks=fetch("http://localhost:8080/home/launch/refresh")
    .then(response=>response.json()).then(array=>{
      $("#attackHistoryTBody").empty();

      array.forEach((e,i)=>{

        let tr=document.createElement("tr")

          let index=document.createElement("td");
          let host=document.createElement("td");
          let port=document.createElement("td");
          let method=document.createElement("td");
          let servers=document.createElement("td");
          let expires=document.createElement("td");
          let status=document.createElement("td");

          index.textContent=i+1;
          host.textContent=e["host"];
          port.textContent=e["port"];
          method.textContent=e["method"];
          servers.textContent=e["servers"];
          expires.textContent=moment(e["expiresOn"]).format("DD-MM-YYYY HH:mm:ss");
          status.textContent=moment().isAfter(moment(e["expiresOn"]))?"Inactive":"Active"


          $(tr).append(index)
          $(tr).append(host)
          $(tr).append(port)
          $(tr).append(method)
          $(tr).append(servers)
          $(tr).append(expires)
          $(tr).append(status)


        $("#attackHistoryTBody").append(tr)
      })
    });

})



