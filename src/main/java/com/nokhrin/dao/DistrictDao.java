package com.nokhrin.dao;

import com.nokhrin.config.HibernateConfig;
import com.nokhrin.model.District;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DistrictDao {
    Session session;
    public void saveDistrict(District district) {

        session = HibernateConfig.getSessionFactory().openSession();
        Transaction tx = null;


        try {
            tx = session.beginTransaction();


            session.save(district);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public District getDistrictById(int id){
        session = HibernateConfig.getSessionFactory().openSession();
        Transaction tx = null;
        District district = null;
        try {
            tx = session.beginTransaction();
            district = (District) session.get(District.class, id);

            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return district;
    }
    public List<District> listDistricts( ){
        session =  HibernateConfig.getSessionFactory().openSession();
        Transaction tx = null;
        List<District> districtList = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            List positions = session.createQuery("FROM District").list();
            for (Iterator iterator = positions.iterator(); iterator.hasNext();){
                districtList.add((District) iterator.next());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return districtList;
    }
}
