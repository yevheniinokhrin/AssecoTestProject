package com.nokhrin.dao;

import com.nokhrin.config.HibernateConfig;
import com.nokhrin.model.Position;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PositionDao {
    Session session;
    public void savePosition(Position position) {

        session = HibernateConfig.getSessionFactory().openSession();
        Transaction tx = null;


        try {
            tx = session.beginTransaction();

            session.save(position);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public Position getPositionById(int id){
        session = HibernateConfig.getSessionFactory().openSession();
        Transaction tx = null;
        Position position = null;
        try {
            tx = session.beginTransaction();
            position = session.get(Position.class, id);

            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return position;
    }

    public List<Position> listPositions( ){
        session =  HibernateConfig.getSessionFactory().openSession();
        Transaction tx = null;
        List<Position> positionList = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            List positions = session.createQuery("FROM Position").list();
            for (Iterator iterator = positions.iterator(); iterator.hasNext();){

                positionList.add((Position) iterator.next());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return positionList;
    }
}
