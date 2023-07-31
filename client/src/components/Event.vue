<template>
  <article
    class="flex max-w-xl flex-col items-start justify-between"
  >
    <div class="flex items-center gap-x-4 text-xs">
      <time
        :datetime="event.firstDate"
        class="text-gray-500"
      >{{ event.firstDate }}</time>
      -
      <time
        :datetime="event.lastDate"
        class="text-gray-500"
      >{{ event.lastDate }}</time>
      <a
        class="relative z-10 rounded-full bg-gray-50 px-3 py-1.5 font-medium text-gray-600 hover:bg-gray-100"
      >{{ event.genres }}</a>

      <a v-if="event.minPrice"
      class="text-gray-500"
      >from ${{ event.minPrice }}
    </a>
    <a v-if="event.maxPrice"
      class="text-gray-500"
      >to ${{ event.maxPrice }}
    </a>
    </div>
    <div class="group relative">
      <h3 class="mt-3 text-lg font-semibold leading-6 text-gray-900 group-hover:text-gray-600">
        <a :href="event.url">
          <span class="absolute inset-0" />
          {{ event.title }}
        </a>
      </h3>
      <p class="mt-5 line-clamp-3 text-sm leading-6 text-gray-600">
        {{ event.description }}
      </p>
    </div>
    <div class="relative mt-8 flex items-center gap-x-4">
      <img
        :src="event.imageUrl"
        alt=""
        class="h-10 w-10 rounded-full bg-gray-50"
      >
      <div class="text-sm leading-6">
        <p class="font-semibold text-gray-900">
          <a :href="event.imageUrl">
            <span class="absolute inset-0" />
            {{ event.theatre }}
          </a>
        </p>
        <p class="text-gray-600">
          {{ event.theatre }}
        </p>
      </div>
    </div>
  </article>
</template>

<script setup>
defineProps({
  event: Object,
})
</script>
