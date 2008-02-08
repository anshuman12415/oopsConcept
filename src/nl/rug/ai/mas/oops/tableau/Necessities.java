/**
  * This program (working title: MAS Prover) is an automated tableaux prover
  * for epistemic logic (S5n).
  * Copyright (C) 2007  Elske van der Vaart and Gert van Valkenhoef

  * This program is free software; you can redistribute it and/or modify it
  * under the terms of the GNU General Public License version 2 as published
  * by the Free Software Foundation.

  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.

  * You should have received a copy of the GNU General Public License along
  * with this program; if not, write to the Free Software Foundation, Inc.,
  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
  */

package nl.rug.ai.mas.oops.tableau;

import java.util.*;
import nl.rug.ai.mas.oops.formula.*;

public class Necessities {
	private Necessities d_parent;
//	private Vector<Node> d_current;
	private Vector<Node> d_current;

	public Necessities(Necessities parent) {
		d_parent = parent;
		d_current = new Vector<Node>();
	}

	public void add(Node n) {
		d_current.add(n);
	}

	public Vector<Node> apply(Label l) {
		Vector<Node> result = null;
		if (d_parent != null)
			result = d_parent.apply(l);
		else
			result = new Vector<Node>();

		for (Node n : d_current) {
			NodeSubstitution nsub = n.getLabel().match(l);
			if (nsub != null) {
				result.add(n.substitute(nsub));
			}
		}
		return result;
	}

	public String toString() {
		String s = new String();
		if (d_parent == null)
			s = "###\n";
		else
			s = d_parent.toString() + "---\n";
		for (Node n : d_current) {
			s += "\t" + n.toString() + "\n";
		}
		return s;
	}
}

