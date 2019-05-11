package com.jhart.gamelog.api.transform;

import com.jhart.gamelog.api.entities.IEntity;
import com.jhart.gamelog.api.model.IModel;

public interface Transformer {
    
  IEntity transform(IModel object);
  IModel transform (IEntity object);

}
