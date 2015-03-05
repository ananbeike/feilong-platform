/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.tools.log4j.spi;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.TriggeringEventEvaluator;

/**
 *
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.8 2014年11月9日 上午3:08:18
 * @since 1.0.8
 */
public class InfoTriggeringEventEvaluator implements TriggeringEventEvaluator{

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.spi.TriggeringEventEvaluator#isTriggeringEvent(org.apache.log4j.spi.LoggingEvent)
	 */
	@Override
	public boolean isTriggeringEvent(LoggingEvent event){
		return event.getLevel().isGreaterOrEqual(Level.INFO);
	}
}