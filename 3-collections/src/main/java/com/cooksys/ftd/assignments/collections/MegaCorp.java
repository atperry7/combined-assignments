package com.cooksys.ftd.assignments.collections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {

	private Set<Capitalist> capitalSet = new HashSet<>();
	/**
	 * Adds a given element to the hierarchy.
	 * <p>
	 * If the given element is already present in the hierarchy, do not add it
	 * and return false
	 * <p>
	 * If the given element has a parent and the parent is not part of the
	 * hierarchy, add the parent and then add the given element
	 * <p>
	 * If the given element has no parent but is a Parent itself, add it to the
	 * hierarchy
	 * <p>
	 * If the given element has no parent and is not a Parent itself, do not add
	 * it and return false
	 *
	 * @param capitalist
	 *            the element to add to the hierarchy
	 * @return true if the element was added successfully, false otherwise
	 */
	@Override
	public boolean add(Capitalist capitalist) {
		//Checks for null OR if the capitalist already exists 
		//Checks if it is not an instance of FatCat and if the capitalist does not have a parent
		if (capitalist == null || this.has(capitalist) 
				|| (!(capitalist instanceof FatCat) && !capitalist.hasParent())) {
			return false;
		}

		//Checks the capitalists for parents and if the parent is already in the structure else if it does have capitalist parent
		//and capitalist parent is in the data structure then adds
		if (capitalist.hasParent() && !this.has(capitalist.getParent())) {
			add(capitalist.getParent());
			capitalSet.add(capitalist);
			return true;
		} else if (capitalist.hasParent() && this.has(capitalist.getParent())) {
			return capitalSet.add(capitalist);
		}
		//Checks for any lone wolf FatCats that don't have parents and that are not in the data structure
		if (capitalist instanceof FatCat && !has(capitalist)) {
			capitalSet.add(capitalist);
			return true;
		}

		return false;
	}

	/**
	 * @param capitalist
	 *            the element to search for
	 * @return true if the element has been added to the hierarchy, false
	 *         otherwise
	 */
	@Override
	public boolean has(Capitalist capitalist) {
		return capitalSet.contains(capitalist);
	}

	/**
	 * @return all elements in the hierarchy, or an empty set if no elements
	 *         have been added to the hierarchy
	 */
	@Override
	public Set<Capitalist> getElements() {
		return new HashSet<>(capitalSet);
	}

	/**
	 * @return all parent elements in the hierarchy, or an empty set if no
	 *         parents have been added to the hierarchy
	 */
	@Override
	public Set<FatCat> getParents() {
		Iterator<Capitalist> capIter = capitalSet.iterator();
		HashSet<FatCat> catParents = new HashSet<>();

		while (capIter.hasNext()) {
			Capitalist capitalist = (Capitalist) capIter.next();
			if (capitalist.getClass() == FatCat.class) {
				catParents.add((FatCat) capitalist);
			}
		}
		
		return catParents;

	}

	/**
	 * @param fatCat
	 *            the parent whose children need to be returned
	 * @return all elements in the hierarchy that have the given parent as a
	 *         direct parent, or an empty set if the parent is not present in
	 *         the hierarchy or if there are no children for the given parent
	 */
	@Override
	public Set<Capitalist> getChildren(FatCat fatCat) {
		Set<Capitalist> slaveSet = new HashSet<>();
		Iterator<Capitalist> childerIter = capitalSet.iterator();
		
		//Returns empty set if parent is not in hierarchy
		if (!has(fatCat)) {
			return slaveSet;
		}

		while (childerIter.hasNext()) {
			Capitalist capitalist = (Capitalist) childerIter.next();
			if (capitalist.getParent() == fatCat) {
				slaveSet.add(capitalist);
			}
		}

		return slaveSet;
	}

	/**
	 * @return a map in which the keys represent the parent elements in the
	 *         hierarchy, and the each value is a set of the direct children of
	 *         the associate parent, or an empty map if the hierarchy is empty.
	 */
	@Override
	public Map<FatCat, Set<Capitalist>> getHierarchy() {
		Map<FatCat, Set<Capitalist>> mapHierarchy = new HashMap<>();
		Set<FatCat> fatCatsToTest = getParents();
		Iterator<FatCat> fatIterator = fatCatsToTest.iterator();

		while (fatIterator.hasNext()) {
			FatCat fatCat = (FatCat) fatIterator.next();
			mapHierarchy.put(fatCat, getChildren(fatCat));

		}

		return mapHierarchy;
	}

	/**
	 * @param capitalist
	 * @return the parent chain of the given element, starting with its direct
	 *         parent, then its parent's parent, etc, or an empty list if the
	 *         given element has no parent or if its parent is not in the
	 *         hierarchy
	 */
	@Override
	public List<FatCat> getParentChain(Capitalist capitalist) {
		List<FatCat> fatCatList = new LinkedList<>();
		
		if (capitalist == null) {
			return fatCatList;
		}

		FatCat fatCat = capitalist.getParent();

		while (fatCat != null && has(capitalist.getParent())) {
			fatCatList.add(fatCat);
			fatCat = fatCat.getParent();
		}

		return fatCatList;
	}
}
