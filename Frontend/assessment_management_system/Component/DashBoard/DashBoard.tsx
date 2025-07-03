'use client'

import assessmentDesignImage from '../../public/assessment.jpeg';
import Link from 'next/link'
import Image from "next/image";
import { ArrowRight, CheckCircle } from 'lucide-react'
import { testimonials } from '../../Dashboard_Features/Testimonials'
import { features } from '../../Dashboard_Features/Features'

export default function LandingPage() {
  
  return (
    <div className="min-h-screen bg-white">
      <section className="bg-gradient-to-br from-blue-50 to-white py-20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mt-20">
            <Image src={assessmentDesignImage} alt="assessmentDesign" className="w-2/2 h-150 mb-50"/>
            <h1 className="text-4xl md:text-6xl font-bold text-gray-900 mb-10">
              Streamline Your
              <span className="text-rose-700"> Assessment </span>
              Process
            </h1>
            <p className="text-xl text-gray-600 mb-8 max-w-3xl mx-auto">
              A comprehensive platform for creating, delivering, and evaluating assessments. 
              Perfect for educators, employers, and learners seeking efficient assessment solutions.
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center mb-30">
              <Link href="/auth/signup" className=" border border-primary text-primary px-8 py-3 mr-5 mt-10 rounded-lg text-lg font-semibold hover:bg-blue-800 hover:text-white transition-colors flex items-center justify-center">
                Get Started Free
                <ArrowRight className="ml-2 w-5 h-5" />
              </Link>
              <Link href="/marketplace" className="border border-primary text-primary px-8 py-3 mr-5 mt-10 rounded-lg text-lg font-semibold hover:bg-blue-800 hover:text-white transition-colors">
                Browse Assessments
              </Link>
            </div>
          </div>
        </div>
      </section>

      <section id="features" className="py-20 bg-gray-300">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-16">
            <h2 className="text-3xl md:text-4xl font-bold text-gray-900 mb-5">
              Powerful Features for Every User
            </h2>
            <p className="text-xl text-gray-600 max-w-3xl font-semibold mx-auto mb-20">
              Whether you're a student, educator, or employer, our platform provides the tools you need to succeed.
            </p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 mb-5">
            {features.map((feature, index) => (
              <div key={index} className="bg-white p-6 rounded-lg shadow-sm hover:shadow-md transition-duration-200 transform hover:scale-105">
                <div className="mb-4 text-orange-500">{feature.icon}</div>
                <h3 className="text-xl font-semibold text-gray-900 mb-2">{feature.title}</h3>
                <p className="text-gray-600">{feature.description}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      <section id="about" className="py-20 bg-white mt-10 mb-20">
        <div className="max-w-7xl mx-auto px-4 sm:px-8 lg:px-8">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
            <div>
              <h2 className="text-3xl md:text-4xl items-center font-bold text-gray-900 mb-10 ">
                Designed for Modern Assessment Needs
              </h2>
              <p className="text-lg text-gray-600 mb-6">
                Our platform addresses the inefficiencies in traditional assessment workflows by providing 
                a centralized, automated solution that serves multiple user types.
              </p>
              <div className="space-y-4 mt-10">
                <div className="flex items-start">
                  <CheckCircle className="w-8 h-8 text-green-500 mt-1 mr-3" />
                  <div>
                    <h4 className="font-semibold text-gray-900">For Students & Job Seekers</h4>
                    <p className="text-gray-600">Take skill-based assessments, track progress, and earn valuable badges</p>
                  </div>
                </div>
                <div className="flex items-start">
                  <CheckCircle className="w-8 h-8 text-green-500 mt-1 mr-3" />
                  <div>
                    <h4 className="font-semibold text-gray-900">For Educators</h4>
                    <p className="text-gray-600">Create and manage quizzes with auto-scoring and detailed feedback</p>
                  </div>
                </div>
                <div className="flex items-start">
                  <CheckCircle className="w-8 h-8 text-green-500 mt-1 mr-3" />
                  <div>
                    <h4 className="font-semibold text-gray-900">For Employers</h4>
                    <p className="text-gray-600">Streamline interviews with instant assessment results</p>
                  </div>
                </div>
              </div>
            </div>
            <div className="bg-gradient-to-br from-blue-100 to-blue-400 p-8 rounded-lg hover:shadow-m transform scale-105 transition-duration-200">
              <div className="text-center">
                <div className="text-4xl font-bold text-blue-700 mb-3">50,000+</div>
                <div className="text-primary mb-5">Assessments Created</div>
                <div className="text-4xl font-bold text-blue-700 mb-3">100,000+</div>
                <div className="text-primary mb-5">Users Worldwide</div>
                <div className="text-4xl font-bold text-blue-700 mb-3">20,000+</div>
                <div className="text-primary mb-5">Badge Issued</div>
                <div className="text-4xl font-bold text-blue-700 mb-3">95%</div>
                <div className="text-primary">Satisfaction Rate</div>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section id="testimonials" className="py-20 bg-orange-100">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-16">
            <h2 className="text-3xl md:text-4xl font-bold text-gray-900 mb-10">
              What Our Users Say
            </h2>
            <p className="text-xl text-gray-600 font-semibold">
              Join thousands of satisfied users who have transformed their assessment process
            </p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8 ">
            {testimonials.map((testimonial, index) => (
              <div key={index} className="bg-white p-6 rounded-lg shadow-sm hover:shadow-md transition-duration-200 transform hover:scale-105">
                <p className="text-gray-600 mb-4 italic">"{testimonial.content}"</p>
                <div className="flex items-center">
                  <div className="w-10 h-10 bg-blue-100 rounded-full flex items-center justify-center mr-3">
                    <span className="text-primary font-semibold">
                      {testimonial.name.split(' ').map(n => n[0]).join('')}
                    </span>
                  </div>
                  <div>
                    <div className="font-semibold text-gray-900">{testimonial.name}</div>
                    <div className="text-gray-600 text-sm">{testimonial.role}</div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      <section className="py-20 mb-30">
        <div className="max-w-4xl mx-auto text-center px-4 sm:px-6 lg:px-8">
          <h2 className="text-3xl md:text-4xl font-bold text-blue-400 mb-6">
            Ready to Transform Your Assessment Process?
          </h2>
          <p className="text-xl text-blue-300 font-semibold mb-8">
            Join thousands of users who have already streamlined their assessment workflows
          </p>
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Link href="/auth/signup" className="border bg-white text-primary px-8 py-3 rounded-lg text-lg font-semibold hover:bg-purple-300 hover:text-white transition-.colors">
              Start Free Trial
            </Link>
            <Link href="/marketplace" className="border bg-white text-primary px-8 py-3 rounded-lg text-lg font-semibold hover:bg-purple-300 hover:text-white transition-colors">
              Explore Platform
            </Link>
          </div>
        </div>
      </section>


      
    </div>
  )
}
