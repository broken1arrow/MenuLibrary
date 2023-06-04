package org.brokenarrow.menu.library.utility;

import org.brokenarrow.menu.library.utility.Item.Pair;

import javax.annotation.Nonnull;

public interface PairFunction<T> {
	@Nonnull
	Pair<T, Boolean> apply();

}