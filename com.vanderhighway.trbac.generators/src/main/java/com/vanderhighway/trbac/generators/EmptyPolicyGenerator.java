package com.vanderhighway.trbac.generators;

import com.vanderhighway.trbac.core.modifier.GeneratorUtil;
import com.vanderhighway.trbac.core.modifier.PolicyModifier;
import com.vanderhighway.trbac.model.trbac.model.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.viatra.query.runtime.api.AdvancedViatraQueryEngine;
import org.eclipse.viatra.query.runtime.emf.EMFScope;
import org.eclipse.viatra.transformation.runtime.emf.modelmanipulation.ModelManipulationException;
import org.eclipse.xtext.xbase.lib.Extension;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;

public class EmptyPolicyGenerator {

	@Extension
	private static TRBACPackage ePackage = TRBACPackage.eINSTANCE;

	public static void main(String[] args) throws IOException, InvocationTargetException, ModelManipulationException, ModelManipulationException, ParseException {

        Resource resource = GeneratorUtil.generateAndSaveResource(ePackage, "empty_policy");
        SecurityPolicy policy = GeneratorUtil.buildBasicSecurityPolicy(ePackage, resource, "DummySecurityPolicy",
                "DummyAuthorizationPolicy", "DummySchedule",
                "2020-01-01", "2021-01-01");

        final AdvancedViatraQueryEngine engine = AdvancedViatraQueryEngine.createUnmanagedEngine(new EMFScope(resource));
        PolicyModifier modifier = new PolicyModifier(engine, (SecurityPolicy) resource.getContents().get(0), resource);

		Map<String, DayOfWeekSchedule> dayOfWeekScheduleMap = new HashMap<>();
		Map<String, Map<Integer, DayOfMonthSchedule>> dayOfMonthScheduleMap = new HashMap();

		TemporalContext always = modifier.addTemporalContext("Always");

		resource.save(Collections.emptyMap());

		modifier.dispose();

		System.out.println("Done!");
	}
}
