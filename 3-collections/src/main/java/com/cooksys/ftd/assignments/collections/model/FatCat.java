package com.cooksys.ftd.assignments.collections.model;

public class FatCat implements Capitalist {
	
	private String name;
	private int salary;
	private FatCat fatCat;

    public FatCat(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    public FatCat(String name, int salary, FatCat owner) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fatCat == null) ? 0 : fatCat.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + salary;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FatCat other = (FatCat) obj;
		if (fatCat == null) {
			if (other.fatCat != null)
				return false;
		} else if (!fatCat.equals(other.fatCat))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (salary != other.salary)
			return false;
		return true;
	}

	
    
    
}
