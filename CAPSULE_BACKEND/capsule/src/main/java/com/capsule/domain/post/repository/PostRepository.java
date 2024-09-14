package com.capsule.domain.post.repository;

import com.capsule.domain.capsule.model.Capsule;
import com.capsule.domain.member.model.Member;
import com.capsule.domain.post.model.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"member"})
    List<Post> findAllByCapsule(Capsule capsule);
}
