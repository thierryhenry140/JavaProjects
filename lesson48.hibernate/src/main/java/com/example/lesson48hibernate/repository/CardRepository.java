package com.example.lesson48hibernate.repository;

import com.example.lesson48hibernate.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {}