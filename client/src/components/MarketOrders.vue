<template>

<div id="market_order">
  <div id="day_trade_table" class="overflow-auto">

  <div>
    <b-dropdown id="dropdown-1" text="Column Selection" class="m-md-2">
      <b-dropdown-form>

        <b-form-checkbox-group
          id="checkbox-group-1"
          v-model="visible_column_keys"
          :options="columns_definitions"
          text-field="label"
          value-field="key"
          name="flavour-1"
        ></b-form-checkbox-group>

      </b-dropdown-form>
    </b-dropdown>
  </div>

  <b-table small striped hover bordered 
    id="my-table"
    :items="visible_orders" 
    :fields="fields" 
    :per-page="perPage" 
    :current-page="currentPage"
    :total-rows="rows">

    <template #cell(margin)="data">
      {{ Intl.NumberFormat('en', { notation: 'compact', maximumSignificantDigits: 3 }).format(data.value) }}
    </template>


    <template #cell()="data">
      {{ Intl.NumberFormat('en', { notation: 'compact' }).format(data.value) }}
    </template>

  </b-table>

  <b-pagination
    v-model="currentPage"
    :total-rows="rows"
    :per-page="perPage"
    aria-controls="my-table"
  ></b-pagination>

  </div>
</div>

</template>

<script>

import axios from 'axios';
import '@formatjs/intl-numberformat/polyfill';
import '@formatjs/intl-numberformat/locale-data/en' // locale-data for en;

export default {
  name: 'MarketOrders',
  data: () => { return {
    orders: [],
    visible_column_keys: [],
    currentPage: 1,
    perPage: 20,
    columns_definitions: [
      {
        key: 'buyPrice',
        label: 'Buy price',
        sortable: true
      },
      {
        key: 'sellPrice',
        label: 'Sell price',
        sortable: true
      },
      {
        key: 'margin',
        label: 'Margin (ratio sell/buy)',
        sortable: true
      },
      {
        key: 'totalIsk',
        label: 'Trade volume (isk)',
        sortable: true
      },
      {
        key: 'totalIskBought',
        label: 'Trade volume (buy, isk)',
        sortable: true
      },
      {
        key: 'totalIskSold',
        label: 'Trade volume (sell, isk)',
        sortable: true
      },
      {
        key: 'totalBuyOrder',
        label: 'Number of buy orders',
        sortable: true
      },
      {
        key: 'totalSellOrder',
        label: 'Number of sell orders',
        sortable: true
      },
      {
        key: 'avgBuy',
        label: 'Average buy (isk)',
        sortable: true
      },
            {
        key: 'avgSell',
        label: 'Average sell (isk)',
        sortable: true
      },

    ]
    }},
  mounted() {

    this.visible_column_keys = this.columns_definitions.map(field => field.key)

    axios.post("http://localhost:8080/marketState", {systemName:"Jita", range:1})
    .then(response => {
      this.orders = response.data
    })
    .catch(reason => console.log(reason))
  },
  computed: {
    rows() {
      return this.orders.length
    },
    fields() {
      return  this.columns_definitions.filter(default_field => 
        this.visible_column_keys.includes(default_field.key) 
      )
    },
    visible_orders() {
      return this.orders
    }
  },
}
</script>