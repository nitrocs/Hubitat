/**
 *  ****************  Weather Dot Gov Parent  ****************
 *
 *  Design Usage:
 *  Making the Weather.gov data usable with Hubitat.
 *
 *  Copyright 2020 Bryan Turcotte (@bptworld)
 *
 *  This App is free.  If you like and use this app, please be sure to mention it on the Hubitat forums!  Thanks.
 *
 *  Remember...I am not a programmer, everything I do takes a lot of time and research!
 *  Donations are never necessary but always appreciated.  Donations to support development efforts are accepted via: 
 *
 *  Paypal at: https://paypal.me/bptworld
 *
 *-------------------------------------------------------------------------------------------------------------------
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 * ------------------------------------------------------------------------------------------------------------------------------
 *
 *  If modifying this project, please keep the above header intact and add your comments/credits below - Thank you! -  @BPTWorld
 *
 *  App and Driver updates can be found at https://github.com/bptworld/Hubitat
 *
 * ------------------------------------------------------------------------------------------------------------------------------
 *
 *  Changes:
 *
 *  V1.0.0 - 04/04/20 - Initial release
 *
 */

def setVersion() {
	state.version = "v1.0.0"
}

definition(
    name:"Weather Dot Gov",
    namespace: "BPTWorld",
    author: "Bryan Turcotte",
    description: "Making the Weather.gov data usable with Hubitat.",
    category: "Convenience",
    iconUrl: "",
    iconX2Url: "",
    iconX3Url: "",
    importUrl: "https://raw.githubusercontent.com/bptworld/Hubitat/master/Apps/Weather%20Dot%20Gov/WDG-parent.groovy"
)

preferences {
     page name: "mainPage", title: "", install: true, uninstall: true
} 

def installed() {
    log.debug "Installed with settings: ${settings}"
    initialize()
}

def updated() {
    log.debug "Updated with settings: ${settings}"
    unsubscribe()
    initialize()
}

def initialize() {
    setVersion()
    log.info "There are ${childApps.size()} child apps"
    childApps.each {child ->
    	log.info "Child app: ${child.label}"
    }
}

def mainPage() {
    display()
    dynamicPage(name: "mainPage") {
    	installCheck()
		if(state.appInstalled == 'COMPLETE'){
			section("Instructions:", hideable: true, hidden: true) {
				paragraph "<b>Information</b>"
				paragraph "Making the Weather.gov data usable with Hubitat."
			}
			section(getFormat("header-green", "${getImage("Blank")}"+" Child Apps")) {
				app(name: "anyOpenApp", appName: "Weather Dot Gov Child", namespace: "BPTWorld", title: "<b>Add a new 'Weather Dot Gov' child</b>", multiple: true)
			}
            
			section(getFormat("header-green", "${getImage("Blank")}"+" General")) {
       			label title: "Enter a name for this parent app", required: false
 			}
			display2()
		}
	}
}

def installCheck(){
    display()
	state.appInstalled = app.getInstallationState() 
	if(state.appInstalled != 'COMPLETE'){
		section{paragraph "${getImage("optionsRed")} <b>Please hit 'Done' to install '${app.label}'</b>"}
  	}
  	else{
    	log.info "${app.label} Installed Successful"
  	}
}

def getImage(type) {					// Modified from @Stephack Code
    def loc = "<img src=https://raw.githubusercontent.com/bptworld/Hubitat/master/resources/images/"
    if(type == "Blank") return "${loc}blank.png height=40 width=5}>"
    if(type == "checkMarkGreen") return "${loc}checkMarkGreen2.png height=30 width=30>"
    if(type == "optionsGreen") return "${loc}options-green.png height=30 width=30>"
    if(type == "optionsRed") return "${loc}options-red.png height=30 width=30>"
    if(type == "cogWithWrench") return "${loc}cogWithWrench.png height=30 width=30>"
    if(type == "logo") return "${loc}shield-checkmark.png height=60>"
}

def getFormat(type, myText=""){			// Modified from @Stephack Code   
	if(type == "header-green") return "<div style='color:#ffffff;font-weight: bold;background-color:#81BC00;border: 1px solid;box-shadow: 2px 3px #A9A9A9'>${myText}</div>"
    if(type == "line") return "<hr style='background-color:#1A77C9; height: 1px; border: 0;'>"
    if(type == "title") return "<h2 style='color:#1A77C9;font-weight: bold'>${myText}</h2>"
}

def display() {
    section (getFormat("title", "${getImage("logo")}" + " Weather Dot Gov")) {
        paragraph "<div style='color:#1A77C9'>Making the Weather.gov data usable with Hubitat.</div>"
		paragraph getFormat("line")
	}
}

def display2(){
    setVersion()
	section() {
		paragraph getFormat("line")
		paragraph "<div style='color:#1A77C9;text-align:center'>Weather Dot Gov - @BPTWorld<br>${state.version}</div>"
	}       
}         
