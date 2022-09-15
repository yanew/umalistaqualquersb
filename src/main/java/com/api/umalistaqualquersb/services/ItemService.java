package com.api.umalistaqualquersb.services;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.umalistaqualquersb.models.Item;
import com.api.umalistaqualquersb.repositories.ItemRepository;


@Service
public class ItemService {

    final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public Optional<Item> findById(UUID id) {
        return itemRepository.findById(id);
    }

    @Transactional
    public void delete(Item item) {
    	itemRepository.delete(item);
    }
}