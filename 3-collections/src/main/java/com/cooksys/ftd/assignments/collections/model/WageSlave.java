package com.cooksys.ftd.assignments.collections.model;

public class WageSlave implements Capitalist {
	
	private String name;
	private int salary;
	private FatCat fatCat;


    public WageSlave(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    public WageSlave(String name, int salary, FatCat owner) {
        this.name = name;
        this.salary = salary;
        this.fatCat = owner;
    }

    /**
     * @return the name of the capitalist
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @return the salary of the capitalist, in dollars
     */
    @Override
    public int getSalary() {
        return this.salary;
    }

    /**
     * @return true if this element has a parent, or false otherwise
     */
    @Override
    public boolean hasParent() {
        if (fatCat != null) {
			return true;
		}
        
        return false;
    }

    /**
     * @return the parent of this element, or null if this represents the top of a hierarchy
     */
    @Override
    public FatCat getParent() {
    	if (hasParent()) {
			return this.fatCat;
		}
    	
        return null;
    }

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WageSlave) {
			WageSlave wageSlaveEquals = (WageSlave) obj;
			
			if (wageSlaveEquals.getSalary() == this.getSalary() 
					&& wageSlaveEquals.getName() == this.getName() 
					&& wageSlaveEquals.getParent() == this.getParent()) {
				return true;
			}
			
		}
		
		return false;
	}
    
    
}
