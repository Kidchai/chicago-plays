<template>
  <aside class="flex overflow-x-auto border-b border-gray-900/5 py-4 lg:block lg:w-64 lg:flex-none lg:border-0 lg:py-20">
    <FilterFieldset label="Search">
      <div>
        <label for="search" class="sr-only"></label>
        <input
          v-model="filters.search"
          type="text"
          name="search"
          class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
          placeholder="Enter a search term"
        >
      </div>
    </FilterFieldset>
    <FilterFieldset label="Price">
      <div>
        <label
          for="min-price"
          class="sr-only"
        >Min price</label>
        <div class="relative mt-2 rounded-md shadow-sm">
          <div class="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
            <span class="text-gray-500 sm:text-sm">$</span>
          </div>
          <input
            v-model="filters.min_price"
            @change="loadEvents"
            type="text"
            name="min-price"
            class="block w-full rounded-md border-0 py-1.5 pl-7 pr-12 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            placeholder="0.00"
            aria-describedby="price-currency"
          >
          <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-3">
            <span class="text-gray-500 sm:text-sm">USD</span>
          </div>
        </div>
      </div>
      <div>
        <label
          for="max-price"
          class="sr-only"
        >Max price</label>
        <div class="relative mt-2 rounded-md shadow-sm">
          <div class="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
            <span class="text-gray-500 sm:text-sm">$</span>
          </div>
          <input
            v-model="filters.max_price"
            @change="loadEvents"
            type="text"
            name="max-price"
            class="block w-full rounded-md border-0 py-1.5 pl-7 pr-12 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            placeholder="0.00"
            aria-describedby="price-currency"
          >
          <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-3">
            <span class="text-gray-500 sm:text-sm">USD</span>
          </div>
        </div>
      </div>
    </FilterFieldset>
    <FilterFieldset label="Date">
      <div>
        <label for="max-date" class="sr-only">From date</label>
        <div class="relative mt-2 rounded-md shadow-sm">
          <input
            v-model="filters.first_date"
            @change="loadEvents"
            type="date"
            name="min-date"
            class="block w-full rounded-md border-0 py-1.5 pl-7 pr-12 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            placeholder="0.00"
            aria-describedby="date-currency"
          >
        </div>
      </div>
      <div>
        <label for="max-date" class="sr-only">To date</label>
        <div class="relative mt-2 rounded-md shadow-sm">
          <input
            v-model="filters.last_date"
            @change="loadEvents"
            type="date"
            name="max-date"
            class="block w-full rounded-md border-0 py-1.5 pl-7 pr-12 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            placeholder="0.00"
            aria-describedby="date-currency"
          >
        </div>
      </div>
    </FilterFieldset>
    <FilterFieldset label="Genre">
      <div class="space-y-5 ml-1">
        <div v-for="genre in genres" :key="genre.id" class="relative flex items-start">
          <div class="flex h-6 items-center">
            <input type="checkbox" v-model="genre.selected" @change="loadEvents" class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-600" />
          </div>
          <div class="ml-3 text-sm leading-6">
            <label for="comments" class="font-medium text-gray-900">{{ genre.genre }}</label>
          </div>
        </div>
      </div>
    </FilterFieldset>

    <div class="mt-5">
      <button
        type="button"
        class="inline-flex items-center rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500"
        @click="loadEvents"
      >
        Search
      </button>
    </div>
  </aside>

  <main class="px-4 py-16 sm:px-6 lg:flex-auto lg:px-0 lg:py-20">
    <div class="mx-auto max-w-2xl space-y-16 sm:space-y-20 lg:mx-0 lg:max-w-none">
      <Event
        v-for="event in events"
        :key="event.id"
        :event="event"
      />
    </div>
  </main>
</template>

<script setup>
import FilterFieldset from './FilterFieldset.vue'
import Event from './Event.vue'
import { ref, onMounted } from 'vue'

const genres = ref([])
const events = ref([])

const filters = {
  search:     null,
  first_date: null,
  last_date:  null,
  min_price:  null,
  max_price:  null,
}

const loadEvents = _ => {
  filters.genres =
    genres.value.
      filter(genre => genre.selected).
      map(genre => genre.id).
      join(',')

  const baseUrl = '/api/events'

  const url = baseUrl + '?' + Object.keys(filters).filter(key => filters[key]).map(key => `${key}=${encodeURIComponent(filters[key])}`).join('&')

  fetch(url)
    .then(response => response.json())
    .then(data => {
      events.value = data
    })
}

const loadGenres = _ => {
  const url = '/api/genres'

  fetch(url)
    .then(response => response.json())
    .then(data => {
      genres.value = data
    })
}

onMounted(() => {
  loadGenres()
  loadEvents()
})

</script>
