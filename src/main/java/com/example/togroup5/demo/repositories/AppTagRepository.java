package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.AppTag;
import org.glassfish.jersey.jaxb.internal.XmlJaxbElementProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppTagRepository extends JpaRepository<AppTag,Long>{

}
