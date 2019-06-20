package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();
    private Object JobForm;

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)

        // TODO #1 - get the Job with the given ID and pass it into the view
       public String index(Model model, int id) {
            model.addAttribute("job", jobData.findById(id));
       return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {
        // TODO #6 - Validate the JobForm model, and if valid
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {


                model.addAttribute(new JobForm());
                return "new-job";
        }

        String myJobName = jobForm.getName();
        Employer myJobEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location myJobLocation = jobData.getLocations().findById(jobForm.getLocationId());
        PositionType myJobPosition = jobData.getPositionTypes().findById(jobForm.getPositionTypesId());
        CoreCompetency myJobCoreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetenciesId());


        Job myNewJob = new Job(myJobName,myJobEmployer, myJobLocation, myJobPosition, myJobCoreCompetency);

        jobData.add(myNewJob);

        int newID = myNewJob.getId();

        model.addAttribute("job", jobData.findById(newID) );

        return "redirect:?id=" + newID;

    }
}
