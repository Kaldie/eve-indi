<template>

<div id="market_order">
  <table class="table table-striped">
  <!-- <thead>
    <tr>
      <th>#</th>
      <th>2018</th>
      <th>2017</th>
    </tr>
  </thead> -->
  <tbody>
    <tr v-for="(order,i) in orders" :key="i">
       <td>{{ order.typeId }}</td> 
       <td>{{ order.price }}</td> 
       <td>{{ order.isBuy }}</td> 
       <td>{{ order.systemId }}</td> 
    </tr>
  </tbody>
</table>
</div>

</template>

<script>
import axios from 'axios';

export default {
  name: 'MarketOrders',
  data: () => { return {orders: null}},
  props: {
    msg: String
  },
  mounted() {
    axios.post("http://localhost:8080/market?systemId=30000142&page=10")
    .then(response => this.orders = response.data.content)
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
